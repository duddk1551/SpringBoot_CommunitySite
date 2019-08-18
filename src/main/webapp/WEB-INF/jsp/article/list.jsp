<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../part/head.jspf"%>
<!-- param.~~해서 request랑 session값을 가져다 쓸 수 있다.  -->

<form action="./list">
	<input type="hidden" name="boardId" value="${param.boardId}">
	
	<div class="con list-top-bar">
		<select name="searchType">
			<option value="title">제목</option>
			<option value="body">내용</option>
			<option value="writer">작성자</option>
		</select>
		
		<c:if test="${param.searchType != null && param.searchKeyword != ''}">
			<script>
				$("select[name='searchType']").val("${param.searchType}");
			</script>
		</c:if>
		
		<input type="text" value="${param.searchKeyword}" name="searchKeyword">
		<select name="sort">
			<option value="latest">최신순</option>
			<option value="oldest">오래된순</option>
			<option value="highViews">높은 조회수</option>
			<option value="lowViews">낮은 조회수</option>
		</select>
		
		<c:if test="${param.sort != null && param.sort != ''}">
			<script>
				$("select[name='sort']").val("${param.sort}");
			</script>
		</c:if>
		<button>검색</button>
	</div>
	
</form>

<div class="list-1 table-common con text-align-center">
	<table>
		<colgroup>
			<col width="80">
			<col width="100">
			<col width="180">
			<col>
			<col width="90">
			<col width="90">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>작성자</th>
				<th>등록날짜</th>
				<th>제목</th>
				<th>댓글</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="article" items="${list}">
				<tr>
					<td>${article.id}</td>
					<td>${article.extra.articleWriter}</td>
					<td>${article.regDate}</td>
					<td><a href="./detail?id=${article.id}&${search}">${article.title }</a></td>
					<td>${article.extra.replyCount}</td>
					<td>${article.view}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="con list-bt-bar">
	<ul>
		<c:if test="${maker.prev}"><a href="./list?cPage=${maker.startPage - 1}&${search}"> ◀ </a></c:if>
		
		<c:forEach begin="${maker.startPage}" end="${maker.endPage}" var="idx">
			<a href="./list?cPage=${idx}&${search}">${idx}</a>
		</c:forEach>
		
		<c:if test="${maker.next }"><a href="./list?cPage=${maker.endPage + 1}&${search}"> ▶ </a></c:if>
	</ul>
</div>

<%@ include file="../part/foot.jspf"%>