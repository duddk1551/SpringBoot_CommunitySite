package com.example.demo.service;

import java.util.Map;

import com.example.demo.dto.Member;

public interface MemberService {

	public Map<String, Object> login(Map<String, Object> param);

	public Member getOne(long id);

	public Map<String, Object> doubleCheck(Map<String, Object> param);

	public Map<String, Object> updateAuthStatus(Map<String, Object> param);

	public Map<String, Object> updateMember(Map<String, Object> param);

	public Map<String, Object> checkedStatus(Map<String, Object> param);

	public void delMember(long id);

	public Map<String, Object> findLoginId(Map<String, Object> param);
	
	public Map<String, Object> findLoginPw(Map<String, Object> param);

	public long getMemberLevel(long loginedMemberId);

}