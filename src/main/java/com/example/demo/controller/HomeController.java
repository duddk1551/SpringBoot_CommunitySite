package com.example.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.Member;
import com.example.demo.service.MemberService;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String showMain() {
		return "home/main";
	}

	@RequestMapping("/home/main")
	public String showMain2() {
		return "home/main";
	}
}