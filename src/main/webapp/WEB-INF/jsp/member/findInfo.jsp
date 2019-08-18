<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="pageTitle" value="내정보 찾기"/>
<%@ include file="../part/head.jspf"%>

<script>
	function findInfoSubmitForm(form) {
		var emailP = /\w+@\w+\.\w+\.?\w*/;


		form.name.value = form.name.value.trim();
		if(form.name.value.length == 0) {
			alert('이름을 입력해주세요.');

			form.name.focus();

			return;
		}


		form.email.value = form.email.value.trim();
		if(form.email.value.length == 0){
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		if(!form.email.value.match(emailP)) {
			alert('이메일 형식에 맞지 않습니다.');
			return false;
		}

		if(form.id.length != 0) {
			form.loginId.value = form.loginId.value.trim();
			if(form.loginId.value.length == 0) {
				alert('아이디를 입력해주세요.');
				form.loginId.focus();

				return;
			}	
		}

		form.submit();
	} 
</script>

<div class="table-common con findInfo-box">
	<h2>아이디 찾기</h2>
	<form action="/member/findLoginId" method="POST" onsubmit="findInfoSubmitForm(this); return false;">
		<table>
			<colgroup>
				<col>
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" placeholder="이름을 입력해주세요." ></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="text" name="email" placeholder="이메일을 입력해주세요."></td>
				</tr>
			</tbody>
		</table>
			<div class="submit-box">
				<button>전송</button>
				<input type="button" value="취소" onclick="location.href='./login';">
			</div>
	</form>
</div>


<div class="table-common con findInfo-box">
	<h2>비밀번호 찾기</h2>
	<form action="/member/findLoginPw" method="POST" onsubmit="findInfoSubmitForm(this); return false;" id="passwdForm">
		<table>
			<colgroup>
			
			</colgroup>
			<tbody>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" placeholder="이름을 입력해주세요."></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td><input id="loginId" type="text" name="loginId" placeholder="아이디를 입력해주세요."></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="text" name="email" placeholder="이메일을 입력해주세요."></td>
				</tr>
			</tbody>
		</table>
		<div class="submit-box">
			<button>전송</button>
			<input type="button" value="취소" onclick="location.href='./login';">
		</div>
	</form>
</div>

<%@ include file="../part/foot.jspf"%>