<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Article"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="pageTitle" value="게시물 수정"/>
<%@ include file="../part/head.jspf"%>

<script>
	function addFile(btn, type1){
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
		file.addEventListener("change", function(){
			
			if(!type.includes(this.files[0].type)){
				alert("이미지 파일이 아닙니다.");
				this.value="";
				$(this).next().next().val("");
				$(this).next().next().next().val("");
			}else{
				$(this).next().next().val(type1);
				$(this).next().next().next().val(this.files[0].type);
			}
		});
	
		button.setAttribute("type", "button");
		button.innerHTML = "제거";
		button.addEventListener("click", function(){			
			$(this).parent().remove();
		});
	
		$(container).append(file,button,inputType, inputType2);
		$(".fileList").append(container);
	}


	function check(box){
		if(box.checked){
			$(box).parent().next().hide();
		}else{
			$(box).parent().next().show();
		}
	}
	<%--function addDeleteList(btn , id){
		if(confirm($(btn).prev().html() + " 을 삭제하시겠 습니까?")){
			var file = document.createElement("input");			
			
			
			file.setAttribute("type", "hidden");
			file.setAttribute("name", "deleteFiles");
			file.setAttribute("value", id);
			
	
			$(".deleteList").append(file);
			$(btn).parent().remove();
		}
	} --%>

	function addModifyIdList(btn, id){
		if(confirm($(btn).prev().prev().html() + " 을 수정하시겠 습니까?")){
			var input = document.createElement("input");			
			var inputType2 = document.createElement("input");	
			var container = document.createElement("div");
			var button = document.createElement("button");
			var type=["image/jpeg", "image/png", "image/gif"];			
			var file = document.createElement("input");			


			button.setAttribute("type", "button");
			button.innerHTML = "취소하기";
			button.addEventListener("click", function(){
				$(this).parent().remove();
				$(btn).show();
				$(btn).prev().show();
			});
			
			input.setAttribute("type", "hidden");
			input.setAttribute("name", "modifyFilesId");
			input.setAttribute("value", id);

			inputType2.setAttribute("type", "hidden");
			inputType2.setAttribute("name", "modifyType2");

			file.setAttribute("type", "file");
			file.setAttribute("name", "modifyFiles");
						
			file.addEventListener("change", function(){
				if(!type.includes(this.files[0].type)){
					alert("이미지 파일만 선택해주세요.");					
					this.value= "";
					$(file).trigger('click');
				}else{
					$(inputType2).val(this.files[0].type);
				}
			});

			
			$(container).append(input, inputType2, "<div>수정된 파일</div>",file, button);
			$(btn).parent().append(container);			
			$(btn).prev().hide();
			$(btn).hide();		
			$(file).trigger('click');
			
		}
	}

</script>


<form class="table-common" action="/article/doModify" method="POST"
	enctype="multipart/form-data">
	<input type="hidden" name="id" value="${article.id}" />
	<input type="hidden" name="boardId" value="${article.boardId}" />
	<table class="detail-1 table-1 con">
		<colgroup>
			<col width="200">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>ID</th>
				<td>${article.id}</td>
			</tr>
			<tr>
				<th>등록날짜</th>
				<td>${article.regDate}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input autocomplete="false" type="text" name="title"
					value="${article.title}" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="body">${article.body}</textarea></td>
			</tr>
			<tr>
				<th>파일</th>
				<td>
					<button type="button" onclick="addFile(this,'body');">파일추가하기</button>
					<div class="fileList"></div>
					<c:forEach items="${files }" var="file">
						<div class="img-box">
							<img src="/article/showImg?id=${file.id}" alt="${file.originFileName}"><br>
							<!-- <button type="button" onclick="addDeleteList(this, ${file.id})">제거하기</button> -->
							<label> 삭제하기 : <input type="checkbox" value="${file.id }" name="deleteFiles" onclick="check(this);"></label>
							<button type="button" onclick="addModifyIdList(this, ${file.id})">수정하기</button>
						</div>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>수정</th>
				<td><button>수정완료</button><input type=button
					value="취소" onclick="history.back();"></td>
			</tr>
		</tbody>
	</table>
</form>
<%@ include file="../part/foot.jspf"%>