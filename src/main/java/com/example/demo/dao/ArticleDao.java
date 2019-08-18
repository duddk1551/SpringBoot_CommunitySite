package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.PageInfo;
import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleFile;

@Mapper
public interface ArticleDao {

	public List<Article> getList(Map<String, Object> param);

	public void add(Map<String, Object> param);
	
	public void addArticleFiles(String prefix, String originFileName, String inputType, String inputType2, Object articleId);

	public Article getOne(Map<String, Object> param);

	public List<Article> getArticles(PageInfo pInfo, Map<String, Object> param);

	public int getTotalCount(Map<String, Object> param);

	public void upViewCount(Map<String, Object> param);

	public List<ArticleFile> getArticleFiles(Map<String, Object> param);

	public ArticleFile getOneFile(int id);

	public Article getOneArticle(Map<String, Object> param);

	public void deleteArticle(Object object);

	public void deleteArticleFiles(Object object);

	public List<ArticleFile> getFiles(List<Integer> deleteFiles);

	public void deleteFiles(List<Integer> deleteFiles);

	public void modifyArticleFiles(String[] modifyFileNames, Integer id, String type2);

	public void modifyArticle(Map<String, Object> param);

	public void like_cnt_up(int articleId);

	public void like_cnt_down(int articleId);

}
