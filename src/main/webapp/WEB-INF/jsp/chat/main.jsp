<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.Chat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="자유 채팅방"/>
<%@ include file="../part/head.jspf"%>
<script>

</script>
<script src="/resource/chat.js"></script>

<div class="con" id="chat-room">
	<div class="message-box">
		<div class="message-group" data-date-str="2019년 08월 27일 화요일">
		
		</div>
	</div>
	<div class="input-box">
		<input type="text" id="text-input">
		<div class="btn-submit">
			<span>전송</span>
		</div>
	</div>
</div>

<%@ include file="../part/foot.jspf"%>