package com.example.demo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LikeDao;
import com.example.demo.dto.Like;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	LikeDao likeDao;
	
	public int getLike(HashMap<String, Object> hashMap) {
		return likeDao.getLike(hashMap);
	}

	public void addLike(HashMap<String, Object> hashMap) {
		likeDao.addLike(hashMap);
	}

	public void cancelLike(HashMap<String, Object> hashMap) {
		likeDao.cancelLike(hashMap);
	}

}
