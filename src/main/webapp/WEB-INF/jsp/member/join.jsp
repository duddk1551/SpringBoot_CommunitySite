<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="pageTitle" value="회원가입" />
<%@ include file="../part/head.jspf"%>

<script>
	var joinFormSubbmited = false;

	function submitAddForm(form){

		if ( joinFormSubbmited ) {
		      alert('처리중입니다.');
		      return;
		   }
		
		var emailP = /\w+@\w+\.\w+\.?\w*/;
		form.loginId.value = form.loginId.value.trim();
		form.loginPw.value = form.loginPw.value.trim();
		form.name.value = form.name.value.trim();
		form.email.value = form.email.value.trim();
		

		if(form.loginId.value.length == 0 || form.loginPw.value.length == 0
				|| form.name.value.length == 0 || form.email.value.length == 0)
		{
			alert("빈칸없이 입력해주세요.");
			return false;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length < 4) {
			alert('비밀번호를 4자 이상 입력해주세요.');
			form.loginPw.focus();
			return;
		}
		form.Pwcheck.value = form.Pwcheck.value.trim();
		if (form.Pwcheck.value.length < 4) {
			alert('비밀번호 확인을 4자 이상 입력해주세요.');
			form.Pwcheck.focus();
			return;
		}
		if (form.loginPw.value != form.Pwcheck.value) {
			alert('비밀번호가 같지 않습니다.');
			form.Pwcheck.value = "";
			form.Pwcheck.focus();
			return;
		}
		
		var email = form.email.value;
		
		if(!email.match(emailP)){
			alert("이메일 형식에 맞지 않습니다.");
			return false;
		}

		form.submit();
		joinFormSubbmited = true;
	}
</script>
<form class="table-common" onsubmit="submitAddForm(this); return false;" action="./doJoin" method="post">
	<table class="table-1 con">
		<colgroup>
			<col width="200">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>ID</th>
				<td><input type="text" name="loginId" placeholder="ID" ></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="loginPw" placeholder="PW"></td>
			</tr>
			<tr>
				<th>Confirm PW</th>
				<td><input type="password" name="Pwcheck" placeholder="Confirm PW"></td>
			</tr>
			<tr>
				<th>Name</th>
				<td><input type="text" name="name" placeHolder="Name"></td>
			</tr>
			<tr>
				<th>E-mail</th>
				<td><input type="text" name="email" placeHolder="E-mail"></td>
			</tr>
			<tr>
				<th>가입</th>
				<td>
					<input type="submit" value="완료">
					<input type="button" onclick="history.back();" value="취소">
				</td>
			</tr>
		</tbody>
	</table>
</form>


<%@ include file="../part/foot.jspf" %>