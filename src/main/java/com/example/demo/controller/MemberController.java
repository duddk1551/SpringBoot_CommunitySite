package com.example.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Member;
import com.example.demo.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;

	@RequestMapping("member/login")
	public String showLogin() {
		return "member/login";
	}

	@RequestMapping("member/doLogin")
	public String doLogin(@RequestParam Map<String, Object> param, HttpSession session, Model model) {
		
		Map<String,Object> status = (Map<String, Object>) memberService.checkedStatus(param);
		
		
		if(status.get("status") == "needToCertification") {
			model.addAttribute("alertMsg", "이메일 인증 필요");
			model.addAttribute("locationReplaceUrl", "/member/login");
		} else if(status.get("status") == "checkLoginInfo") {
			model.addAttribute("alertMsg", "일치하는 회원정보가 없습니다.");
			model.addAttribute("locationReplaceUrl", "/member/login");
		} else {
			Map<String, Object> rs = memberService.login(param);

			String resultCode = (String) rs.get("resultCode");
			String msg = (String) rs.get("msg");
			model.addAttribute("alertMsg", msg);

			String redirectUrl = (String) param.get("redirectUrl");

			if (redirectUrl == null || redirectUrl.length() == 0) {
				redirectUrl = "/member/myPage";
			}

			if (resultCode.startsWith("S-")) {
				model.addAttribute("locationReplaceUrl", redirectUrl);
				session.setAttribute("loginedMemberId", rs.get("loginedMemberId"));
			} else {
				model.addAttribute("historyBack", true);
			}
		}

		return "common/redirect";
	}

	@RequestMapping("member/join")
	public String showJoin() {
		return "member/join";
	}
	
	@RequestMapping("member/doJoin")
	public String doJoin(@RequestParam Map<String, Object> param, Model model) {
		
		Map<String,Object> rs = memberService.doubleCheck(param);
		String resultCode = (String) rs.get("resultCode");
		String redirectUrl = (String) param.get("redirectUrl");
		
		if(redirectUrl == null || redirectUrl.length() == 0) {
			redirectUrl = "/member/login";
		}
		model.addAttribute("alertMsg", rs.get("msg"));
		
		if(resultCode.startsWith("S-")) {
			model.addAttribute("locationReplaceUrl", redirectUrl);
		}else {
			model.addAttribute("historyBack", true);
		}
		
		return "common/redirect";
	}
	
	@RequestMapping("member/confirm")
	public String confirm(@RequestParam Map<String,Object> param, Model model) {
		Map<String, Object> rs = memberService.updateAuthStatus(param);
		String msg = (String)rs.get("msg");
		String resultCode = (String)rs.get("resultCode");
		
		model.addAttribute("alertMsg", msg);
		
		String redirectUrl = "/member/login";
		model.addAttribute("locationReplaceUrl", redirectUrl);
		
		return "common/redirect";
	}
	
	@RequestMapping("member/findLoginId")
	public String findLoginId(@RequestParam Map<String,Object> param, Model model) {
		Map<String, Object> rs = memberService.findLoginId(param);
		String msg = (String)rs.get("msg");
		String resultCode = (String)rs.get("resultCode");
		String redirectUrl = "/member/login";
		
		
		model.addAttribute("alertMsg", msg);
		
		if(resultCode.startsWith("S-")) {
			model.addAttribute("locationReplaceUrl", redirectUrl);
		}else {
			model.addAttribute("historyBack", true);
		}
		
		return "common/redirect";
	}
	
	@RequestMapping("member/findInfo")
	public String findInfo() {
		return "member/findInfo";
	}
	
	@RequestMapping("member/findLoginPw")
	public String findLoginPw(@RequestParam Map<String,Object> param, Model model) {
		
		param.put("where__findPw", true);
		Map<String, Object> rs = memberService.findLoginPw(param);
		String msg = (String)rs.get("msg");
		String resultCode = (String)rs.get("resultCode");
		String redirectUrl = "/member/login";
		
		
		model.addAttribute("alertMsg", msg);
		
		if(resultCode.startsWith("S-")) {
			model.addAttribute("locationReplaceUrl", redirectUrl);
		}else {
			model.addAttribute("historyBack", true);
		}
		
		return "common/redirect";
	}

	@RequestMapping("member/doLogout")
	public String doLogout(HttpSession session, Model model) {
		session.removeAttribute("loginedMemberId");
		model.addAttribute("alertMsg", "로그아웃 되었습니다.");
		model.addAttribute("locationReplaceUrl", "/member/login");
		return "common/redirect";
	}

	@RequestMapping("member/myPage")
	public String myPage(Model model, HttpSession session) {
		long loginedMemberId = (long) session.getAttribute("loginedMemberId");
		Member member = memberService.getOne(loginedMemberId);
		model.addAttribute("member", member);
		return "member/myPage";
	}

	@RequestMapping("member/modify")
	public String modify(Model model, HttpSession session) {
		long loginedMemberId = (long) session.getAttribute("loginedMemberId");
		Member member = memberService.getOne(loginedMemberId);
		model.addAttribute("member", member);
		return "member/modify";
	}
	
	@RequestMapping("member/doModify")
	public String doModify(@RequestParam Map<String,Object> param, Model model) {
		//param에 담기는거 member.id,loginPw,newPw,name,email
		
		Map<String,Object> rs = memberService.updateMember(param);
	
		String resultCode = (String) rs.get("resultCode");
		String redirectUrl = (String) param.get("redirectUrl");
		
		if(redirectUrl == null || redirectUrl.length() == 0) {
			redirectUrl = "/member/myPage";
		}
		model.addAttribute("alertMsg", rs.get("msg"));
		
		if(resultCode.startsWith("S-")) {
			model.addAttribute("locationReplaceUrl", redirectUrl);
		}else {
			model.addAttribute("historyBack", true);
		}
		
		return "common/redirect";
	}
	
	@RequestMapping("member/delMember")
	public String delMember(HttpSession session, Model model) {
		
		long loginedMemberId = (long)session.getAttribute("loginedMemberId");
		
		memberService.delMember(loginedMemberId);
		
		model.addAttribute("alertMsg", "탈퇴 성공 !");
		model.addAttribute("locationReplaceUrl", "/member/doLogout");
		
		return "common/redirect";
	}
}