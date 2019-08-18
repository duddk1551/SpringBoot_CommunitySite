package com.example.demo.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Like;

@Mapper
public interface LikeDao {

	public int getLike(HashMap<String, Object> hashMap);

	public void addLike(HashMap<String, Object> hashMap);

	public void cancelLike(HashMap<String, Object> hashMap);

}
