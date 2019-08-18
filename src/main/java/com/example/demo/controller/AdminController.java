package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.Member;
import com.example.demo.service.AdminService;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	@Autowired
	ArticleService articleService;
	@Autowired
	MemberService memberService;
	
	@RequestMapping("admin/adminPage")
	public String admin(@RequestParam Map<String,Object> param, Model model) {
		
		List<Member> members = adminService.getAllMembers(param);
		
		model.addAttribute("memberList", members);
		
		return "admin/adminPage";
	}
	
	@RequestMapping("admin/deleteMember")
	public String deleteMember(@RequestParam(value="memberId", defaultValue="0") int memberId, Model model) {
		
		memberService.delMember(memberId);
		
		model.addAttribute("alertMsg", "탈퇴시켰습니다.");
		model.addAttribute("locationReplaceUrl", "admin/adminPage");
		
		return "common/redirect";
	}
	
	@RequestMapping("admin/doModifyLevel")
	public String doModifyLevel(@RequestParam Map<String,Object> param, HttpSession session, Model model) {
		//접속자의 멤버 레벨이 0이 아니면 권한체크
		
		long loginedMemberId = (long)session.getAttribute("loginedMemberId");
		long memberLevel = memberService.getMemberLevel(loginedMemberId);	
		
		if(memberLevel != 0) {
			model.addAttribute("alertMsg", "접근 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		} else {
			adminService.modifyLevel(param);
			model.addAttribute("alertMsg", "업데이트 되었습니다.");
			model.addAttribute("locationReplaceUrl", "/admin/adminPage");
			return "common/redirect";
		} 
	}
}
