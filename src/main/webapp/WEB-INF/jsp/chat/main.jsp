<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.demo.dto.ChatMessage"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="자유 채팅방"/>
<%@ include file="../part/head.jspf"%>
<script>
	var chatWriter = '${loginedMember.name}';
	var loginedMemberId = ${loginedMember.id}
</script>
<script src="/resource/chat.js"></script>
<style>
#chat-room {
	border:1px solid black;
	height:700px;
	position:relative;
	margin-bottom:100px;
	margin-top:50px;
}

.bubbleWrapper {
	padding: 10px 10px;
	display: flex;
	justify-content: flex-end;
	flex-direction: column;
	align-self: flex-end;
}
.inlineContainer {
  display: inline-flex;
}
.inlineContainer.own {
  flex-direction: row-reverse;
}
.ownBubble {
	min-width: 60px;
	max-width: 700px;
	padding: 14px 18px;
 	margin: 6px 8px;
	color: #666d78;
	background-color: #EAF2FF;
	border-radius: 16px 16px 0 16px;
	border: 1px solid #D6E5FC;
 
}
.otherBubble {
	min-width: 60px;
	max-width: 700px;
	padding: 14px 18px;
 	margin: 6px 8px;
	color: #666d78;
	background-color: #F9F9F9;
	border-radius: 16px 16px 16px 0;
	border: 1px solid #e8e8e8;
  
}
.own {
	align-self: flex-end;
}
.other {
	align-self: flex-start;
}
.inlineContainer section > i{
	font-size:1.5rem;
}

.inlineContainer > span.own {
}

#chat-room .message-box {
    padding: 0 0.7rem;
    overflow-y: scroll;
    height: calc(100% - 4.4rem);
}
#chat-room .input-box {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
}
#chat-room .input-box #text-input {
    width:100%;
    display:block;
    height:40px;
    outline:none;
    padding-right:8.5rem;
    padding-left:0.5rem;
    padding-top:0.1rem;
    line-height:4rem;
    box-sizing:border-box;
}
#chat-room .input-box .btn-submit {
    position: absolute;
    right:0rem;
    top:50%;
    transform:translateY(-50%);
    padding:10px;
    color:white;
    background-color:pink;
    border-radius: 3px;
    font-size: 0.8rem;
    user-select: none;
}
</style>

<div class="con" id="chat-room">
	<div class="message-box">
		<div class="message-group">
			
		</div>
	</div>
	<div class="input-box">
		<input type="text" id="text-input" placeholder="내용을 입력해주세요.." autocomplete="off">
		<div class="btn-submit">
			<span>전송</span>
		</div>
	</div>
</div>

<%@ include file="../part/foot.jspf"%>