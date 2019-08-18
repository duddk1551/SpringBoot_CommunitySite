package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.ArticleReply;

public interface ArticleReplyService {

	public void addReply(Map<String, Object> param);

	public Map<String,Object> deleteReply(Map<String, Object> param);

	public List<ArticleReply> getArticleReplies(Map<String, Object> param);

	public Map<String, Object> modifyReply(Map<String, Object> param);

}
