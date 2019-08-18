<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="pageTitle" value="마이페이지" />
<%@ include file="../part/head.jspf"%>

<script>
	function checkedForm(form) {
		if (confirm('탈퇴하시겠습니까?')) {
			location.replace('/member/delMember');
		}
		return;
		
	}
</script>

<div class="table-basic con">
	<div class="list-1 table-common con">
		<div><c:out value="${member.name}"/>님의 마이페이지 입니다.</div>
		<table>
			<colgroup>
				<col width="250">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>ID</th>
					<td>${member.loginId}</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>${member.name}</td>
				</tr>
				<tr>
					<th>E-mail</th>
					<td>${member.email}</td>
				</tr>
				<tr>
					<th>회원 등급</th>
					<td>${member.memberLevel}</td>
				</tr>					
			</tbody>
		</table>
		<div class="mypage-btn">
			<a href="javascript:;" onclick="checkedForm(this);">회원탈퇴</a>
			<a href="/member/modify">회원정보수정</a>		
		</div>
	</div>
</div>

<%@ include file="../part/foot.jspf"%>