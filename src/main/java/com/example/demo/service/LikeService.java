package com.example.demo.service;

import java.util.HashMap;

import com.example.demo.dto.Like;

public interface LikeService {

	public int getLike(HashMap<String, Object> hashMap);

	public void addLike(HashMap<String, Object> hashMap);

	public void cancelLike(HashMap<String, Object> hashMap);

}
