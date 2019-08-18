package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ArticleReplyDao;
import com.example.demo.dto.ArticleReply;

@Service
public class ArticleReplyServiceImpl implements ArticleReplyService {
	
	@Autowired
	ArticleReplyDao articleReplyDao;
	
	public void addReply(Map<String,Object> param) {
		articleReplyDao.addReply(param);
	}
	
	public Map<String,Object> deleteReply(Map<String,Object> param) {
		
		ArticleReply reply = articleReplyDao.getOneReply(param.get("id"));
		long memberId = (long) param.get("loginedMemberId");
		
		String msg = "";
		String resultCode = "";
		
		if(reply == null) {
			msg = "존재하지 않는 댓글입니다.";
			resultCode = "F-4";
		} else if(reply.getMemberId() != memberId) {
			msg = "권한이 없습니다.";
			resultCode = "F-4";
		} else {
			articleReplyDao.deleteReply(param);
			msg = "댓글이 삭제되었습니다.";
			resultCode = "S-4";
		}
		
		
		return Maps.of("msg", msg, "resultCode", resultCode);
	}

	public List<ArticleReply> getArticleReplies(Map<String, Object> param) {
		return articleReplyDao.getArticleReplies(param);
	}

	public Map<String, Object> modifyReply(Map<String, Object> param) {
		
		ArticleReply reply = articleReplyDao.getOneReply(param.get("id"));
		long memberId = (long) param.get("loginedMemberId");
		
		String msg = "";
		String resultCode = "";
		
		if(reply == null) {
			msg = "존재하지 않는 댓글입니다.";
			resultCode = "F-4";
		} else if(reply.getMemberId() != memberId) {
			msg = "권한이 없습니다.";
			resultCode = "F-4";
		} else {
			articleReplyDao.modifyReply(param);
			msg = "댓글이 수정되었습니다.";
			resultCode = "S-4";
		}
		
		return Maps.of("msg", msg, "resultCode", resultCode);
	}
}
