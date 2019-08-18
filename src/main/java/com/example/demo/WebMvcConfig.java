package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	@Qualifier("beforeActionInterceptor")
	HandlerInterceptor beforeActionInterceptor;

	@Autowired
	@Qualifier("needToLoginInterceptor")
	HandlerInterceptor needToLoginInterceptor;

	@Autowired
	@Qualifier("needToLogoutInterceptor")
	HandlerInterceptor needToLogoutInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**");

		registry.addInterceptor(needToLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/")
		.excludePathPatterns("/","/home/main", "/common/**")
		.excludePathPatterns("/article/getAllReplies")
		.excludePathPatterns("/member/logout", "/member/join", "/member/confirm", "/member/findLoginId")
		.excludePathPatterns("/member/doJoin", "/member/login", "/member/doLogin", "/member/findLoginPw")
		.excludePathPatterns("/member/findInfo")
		.excludePathPatterns( "/resource/**");

		registry.addInterceptor(needToLogoutInterceptor).addPathPatterns("/member/login")
				.addPathPatterns("/member/doLogin").addPathPatterns("/member/join").addPathPatterns("/member/doJoin");
	}

}