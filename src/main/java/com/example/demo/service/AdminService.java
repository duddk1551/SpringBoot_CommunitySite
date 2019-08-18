package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.Member;

public interface AdminService {

	public List<Member> getAllMembers(Map<String,Object> param);

	public void modifyLevel(Map<String, Object> param);

}
