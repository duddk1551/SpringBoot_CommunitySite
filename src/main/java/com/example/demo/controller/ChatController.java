package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Chat;
import com.example.demo.service.MemberService;

@Controller
public class ChatController {
	private List<Chat> messages;
	
	@Autowired
	MemberService memberService;
	
	ChatController() {
		messages = new ArrayList<>();
	}
	
	@RequestMapping("/chat/home")
	public String showChatHome(Model model, HttpSession session) {
		
		String loginedMemberLoginId = (String)session.getAttribute("loginedMemberLoginId");
		Map<String,Object> param = new HashMap<>();
		param.put("writer", loginedMemberLoginId);
		model.addAttribute("writer", param);
		
		return "chat/main";
	}
	
	@RequestMapping("/chat/addMessage")
	@ResponseBody
	public Map<String,Object> addMessage(String writer, String body) {
		long id = messages.size();
		
		System.out.println("채팅 파라미터 값!!! writer : " + writer + ", body : " + body);
		Chat newMessage = new Chat(id,writer,body);
		messages.add(newMessage);
		
		Map<String,Object> rs = new HashMap<>();
		
		rs.put("msg", "채팅 메세지가 추가되었습니다.");
		rs.put("resultCode", "S-1");
		rs.put("addedMessage", newMessage);
		
		return rs;
	}
	
	@RequestMapping("/chat/getAllMessages")
	@ResponseBody
	public List<Chat> getAllMessages() {
		return messages;
	}
	
	@RequestMapping("/chat/getMessages")
	@ResponseBody
	public List<Chat> getMessages(int from) {
		return messages.subList(from, messages.size());
	}
	
	@RequestMapping("/chat/clearMessage")
	@ResponseBody
	public Map<String,Object> clearMessage() {
		messages.clear();
		
		Map<String,Object> rs = new HashMap<>();
		
		rs.put("msg", "메세지가 모두 삭제되었습니다.");
		rs.put("resultCode", "S-1");
		
		return rs;
	}
}
