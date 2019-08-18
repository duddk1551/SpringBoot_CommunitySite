<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Article"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 상세페이지"/>
<%@ include file="../part/head.jspf"%>
<script>
	var articleId = ${param.id}
	var loginedMemberId = ${loginedMemberId}
</script>
<script src="/resource/reply.js"></script>
<script src="/resource/like.js"></script>

<div class="article-detail table-common con">
	<table>
		<colgroup>
			<col width="80">
		</colgroup>
		<tbody>
			<tr>
				<th>ID</th>
				<td><c:out value="${article.id}" /></td>
			</tr>
			<tr>
				<th>날짜</th>
				<td><c:out value="${article.regDate}" /></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${article.view}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><c:out value="${article.title}" escapeXml="true" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
				${article.bodyForPrint}
				<div class="img-list-box con">
					<c:forEach items="${files}" var="file">
					
							<div class="img-box">
								<img src="/article/showImg?id=${file.id}" alt="${file.originFileName}"><br>
								<a href="./downloadFile?id=${file.id}">${file.originFileName}</a>
							</div>
					</c:forEach>
				</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>


<div class="con" id="like-table">
	<div class="img-box like-img-box">
		<a href="javascript:like_func();">
			<c:if test="${likeAlready}">
				<img class="like_img" src="${pageContext.request.contextPath}/images/like.png">
			</c:if>
			<c:if test="${likeAlready == false}">
				<img class="like_img" src="${pageContext.request.contextPath}/images/dislike.png">
			</c:if>
		</a>
	</div>
	<div id="like_cnt">
		<c:out value="${article.likes}" />
	</div>
</div>

<div class="table-reply con">
	<div>
	<c:if test="${logined}">
		<span>comment</span>
		<form class="add-reply" action="./addReply" method="POST" onsubmit="addReply(this); return false;">
			<input id="articleId" type="hidden" name="articleId" value="${param.id}">
			<input type="hidden" name="boardId" value="${param.boardId }">
			<input id="content" type="text" name="body" placeholder="내용을 입력하세요.">
			<input type="submit" name="submit-btn" value="입력"><input type="reset" value="취소" >
		</form>
	</c:if>
	
	
	<div class="reply-table">
		<table>
			<colgroup>
				<col width="40">
				<col width="100">
				<col width="100">
				<col>
				<col width="100">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>날짜</th>
					<th>작성자</th>
					<th>내용</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody class="reply-body">
			
			</tbody>
		</table>
	</div>
	</div>
</div>



<%@ include file="../part/foot.jspf"%>