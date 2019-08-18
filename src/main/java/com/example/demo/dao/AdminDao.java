package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Member;


@Mapper
public interface AdminDao {

	public List<Member> getAllMembers(Map<String, Object> param);

	public void modifyLevel(Map<String, Object> param);

}
