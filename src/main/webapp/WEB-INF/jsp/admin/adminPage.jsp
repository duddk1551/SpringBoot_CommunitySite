<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="pageTitle" value="관리자페이지" />
<%@ include file="../part/head.jspf"%>
<script src="/resource/admin.js"></script>

<style>
.admin-member-list tr .edit-mode-visible {
	display:none;
}

.admin-member-list tr.edit-mode .edit-mode-visible {
	display:block;
}

.admin-member-list tr.edit-mode .read-mode-visible {
	display:none;
}
</style>

<script>
	function delMember(memberId) {
		if(confirm('탈퇴시키겠습니까?')) {
			location.replace('./deleteMember?memberId=' + memberId);
		}
		return;
	}

	function enableLevelEditMode(el) {
		var $tr = $(el).closest('tr');
		$tr.addClass('edit-mode');
	}

	function disableLevelEditMode(el) {
		var $tr = $(el).closest('tr');
		$tr.removeClass('edit-mode');
	} 
</script>

<div class="admin-memberList con table-common">
<div class="con"><h2>회원 목록</h2></div>
<form action="./adminPage">
	<input type="hidden" name="boardId" value="${param.boardId}">
	
	<div class="con list-top-bar">
		
		<select name="sort">
			<option value="highLevel">높은레벨순</option>
			<option value="lowLevel">낮은레벨순</option>
			<option value="newMember">최근가입일순</option>
			<option value="originMember">오래된가입일순</option>
		</select>
		
		<c:if test="${param.sort != null && param.sort != ''}">
			<script>
				$("select[name='sort']").val("${param.sort}");
			</script>
		</c:if>
		<button>검색</button>
	</div>
	
</form>

	<table class="admin-member-list text-align-center">
		<colgroup>
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>가입일</th>
				<th>아이디</th>
				<th>이름</th>
				<th>회원레벨</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="member" items="${memberList}">
				<tr>
					<td>${member.id}</td>
					<td>${member.regDate}</td>
					<td>${member.loginId}</td>
					<td>${member.name}</td>
					<td>
						<div class="read-mode-visible">
							${member.memberLevel}
							<a href="javascript:" onclick="enableLevelEditMode(this);">변경</a>	
						</div>
						<div class="edit-mode-visible">
							<form action="./doModifyLevel" method="POST" >
								<input type="hidden" value="${member.id}" name="id">
								<input type="text" value="${member.memberLevel}" name="modifyLevel" style="width:10px;">
								<input type="submit" value="수정">
								<input type="reset" value="수정취소" onclick="disableLevelEditMode(this);">
							</form>
						</div>
					</td>
					<td><button type="button" onclick="delMember(${member.id});">탈퇴</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>