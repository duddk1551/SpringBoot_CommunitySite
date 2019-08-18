package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.PageInfo;
import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleFile;

public interface ArticleService {


	public long add(Map<String, Object> param);

	public Article getOne(Map<String, Object> param);
	
	public List<Article> getArticles(PageInfo pInfo, Map<String, Object> param);

	public int getTotalCount(Map<String, Object> param);

	public void upViewCount(Map<String, Object> param);

	public List<ArticleFile> getArticleFiles(Map<String, Object> param);

	public void addArticleFiles(List<MultipartFile> files, List<String> inputType, List<String> inputType2, Object articleId) throws IOException;

	public ArticleFile getOneFile(int id);

	public Map<String, Object> deleteArticle(Map<String, Object> param, Object memberId);

	public Map<String, Object> modifyArticle(Map<String, Object> param, List<MultipartFile> files,
			List<Integer> deleteFiles, List<MultipartFile> modifyFiles, List<Integer> modifyFilesId,
			List<String> inputType, List<String> inputType2, List<String> modifyInputType, Object attribute) throws IOException;

	public void like_cnt_up(int articleId);

	public void like_cnt_down(int articleId);

}
