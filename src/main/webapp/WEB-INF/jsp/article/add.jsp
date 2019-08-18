<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Article"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="pageTitle" value="게시물 추가"/>
<%@ include file="../part/head.jspf"%>

<script>
	function addFile(btn, k) {
		var file = document.createElement("input");
		var button = document.createElement("button");
		var container = document.createElement("div");
		var inputType = document.createElement("input");
		var inputType2 = document.createElement("input");
		var type=["image/jpeg", "image/png", "image/gif"];

		inputType.setAttribute("type", "hidden");
		inputType.setAttribute("name", "type");

		inputType2.setAttribute("type", "hidden");
		inputType2.setAttribute("name", "type2");

		file.setAttribute("type", "file");
		file.setAttribute("name", "files");
		file.addEventListener("change", function() {

			if(!type.includes(this.files[0].type)) {
				alert("이미지 파일이 아닙니다.");
				this.value="";
				$(this).next().val("");
				$(this).next().next().val("");
			} else {
				$(this).next().next().val(k);
				$(this).next().next().next().val(this.files[0].type);
			}
		});

		button.setAttribute("type", "button");
		button.innerHTML = "제거";
		button.addEventListener("click", function() {
			$(this).parent().remove();
		});

		$(container).append(file,button,inputType,inputType2);
		$(".fileList").append(container);
		
	}

	var joinFormSubbmited = false;

	function submitAddForm(form) {
	   if ( joinFormSubbmited ) {
	      alert('처리중입니다.');
	      return;
	   }

	   form.title.value = form.title.value.trim();

		if(form.title.value.length == 0) {
			alert('제목을 입력해 주세요.');
			form.title.focus();
			return false;
		}

		form.body.value = form.body.value.trim();

		if(form.body.value.length == 0) {
			alert('내용을 입력해 주세요.');
			form.body.focus();
			return false;
		}
	   
	   form.submit();
	   joinFormSubbmited = true;
	}
	
</script>

<form class="table-common" action="/article/doAdd" method="POST"
	enctype="multipart/form-data" onsubmit="submitAddForm(this); return false;">
	<input type="hidden" name="boardId" value="${param.boardId}">
	<table class="table-1 con">
		<colgroup>
			<col width="200">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>제목</th>
				<td><input autocomplete="false" type="text" name="title" placeholder="제목을 입력해주세요.">
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="body" placeholder="내용을 입력해주세요." ></textarea>
				</td>
			</tr>
			<tr>
				<th>파일</th>
				<td>
					<button type="button" onclick="addFile(this,'body');">파일추가하기</button>
					<div class="fileList"></div>
				</td>
			</tr>
			<tr>
				<th>등록</th>
				<td><input type="submit" value="등록"> <input type=button
					value="취소" onclick="history.back();"></td>
			</tr>
		</tbody>
	</table>
</form>


<%@ include file="../part/foot.jspf"%>