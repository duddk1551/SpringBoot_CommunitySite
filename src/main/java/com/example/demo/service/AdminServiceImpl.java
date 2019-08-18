package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDao;
import com.example.demo.dto.Member;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminDao adminDao;
	
	public List<Member> getAllMembers(Map<String, Object> param) {
		return adminDao.getAllMembers(param);
	}


	public void modifyLevel(Map<String, Object> param) {
		adminDao.modifyLevel(param);
	}

}
