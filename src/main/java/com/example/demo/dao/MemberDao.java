package com.example.demo.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Member;

@Mapper
public interface MemberDao {

	public Member getMatchedOne(Map<String, Object> param);

	public Member getOne(long loginedMemberId);

	public Member getEmailMember(Map<String, Object> param);

	public void updateAuthStatus(Map<String, Object> param);

	public int doubleCheck(Map<String, Object> param);

	public void addMember(Map<String, Object> param);

	public void updateMember(Map<String, Object> param);

	public int checkedPw(String checkedPw);

	public void delMember(long id);

	public Member findMemberInfo(Map<String, Object> param);

	public void modifyNewPw(Map<String, Object> param);

}