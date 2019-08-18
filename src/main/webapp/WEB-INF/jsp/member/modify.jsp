<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="pageTitle" value="회원정보수정" />
<%@ include file="../part/head.jspf"%>

<script>
	function submitModifyForm(form) {
		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return false;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일주소를 입력해주세요.');
			form.email.focus();
			return false;
		}

		form.submit();
	}
</script>

<div class="login-box con table-common">
	<form class="table-common" action="../member/doModify" method="POST"
		onsubmit="submitModifyForm(this); return false;">
		<input type="hidden" name="id" value="${member.id}" />
		<table class="detail-1 table-1 con">
			<colgroup>
				<col width="200">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>아이디</th>
					<td>${member.loginId}</td>
				</tr>
				<tr>
					<th>기존 비밀번호</th>
					<td><input type="password" name="loginPw" autocomplete="off"></td>
				</tr>
				<tr>
					<th>새로운 비밀번호</th>
					<td><input type="password" name="newPw" autocomplete="off"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" autocomplete="off" value="${member.name}"></td>
				</tr>
				<tr>
					<th>E-mail</th>
					<td><input type="text" name="email" autocomplete="off" value ="${member.email}"></td>
				</tr>					
				<tr>
					<th>수정</th>
					<td><input type="submit" value="수정" /> <input type=button
						value="취소" onclick="history.back();"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<%@ include file="../part/foot.jspf"%>