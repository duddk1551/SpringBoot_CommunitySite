package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.ArticleReply;

@Mapper
public interface ArticleReplyDao {

	public void addReply(Map<String, Object> param);

	public void deleteReply(Map<String, Object> param);

	public List<ArticleReply> getArticleReplies(Map<String, Object> param);

	public ArticleReply getOneReply(Object object);

	public void modifyReply(Map<String, Object> param);

}
