package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.PageInfo;
import com.example.demo.dao.ArticleDao;
import com.example.demo.dao.MemberDao;
import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleFile;
import com.example.demo.util.CUtil;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	ArticleDao articleDao;
	@Autowired
	MemberDao memberDao;
	@Value("${custom.uploadDir}")
	private String filePath;

	
	public long add(Map<String,Object> param) {
		articleDao.add(param);
		
		return CUtil.getAsLong(param.get("id"));
	}
	
	public Article getOne(Map<String,Object> param) {
		return articleDao.getOne(param);
	}
	
	public Map<String, Object> deleteArticle(Map<String, Object> param, Object memberId) {
		Article article = articleDao.getOneArticle(param);
		long memberLevel = (long)param.get("memberLevel");
		String msg = "";
		String resultCode = "";
		
		if(article == null) {
			msg = "존재하지 않는 게시물 입니다.";
			resultCode = "F-3";
			
			return Maps.of("msg", msg, "resultCode", resultCode);
		}
		
		
		if(article.getMemberId() == (long)memberId || memberLevel == 0) {
			List<ArticleFile> files = articleDao.getArticleFiles(param);
			
			for(ArticleFile file : files) {
				File target = new File(filePath, file.getPrefix() + file.getOriginFileName());
				if(target.exists()) {
					target.delete();
				}
			}
			
			articleDao.deleteArticle(param.get("id"));
			articleDao.deleteArticleFiles(param.get("id"));
			msg = "삭제되었습니다.";
			resultCode = "S-3";
			
			return Maps.of("msg",msg, "resultCode",resultCode);
		} else {
			msg = "권한이 없습니다.";
			resultCode = "F-3";
			
			return Maps.of("msg", msg, "resultCode", resultCode);
		}
		
	}

	public List<Article> getArticles(PageInfo pInfo, Map<String, Object> param) {
		return articleDao.getArticles(pInfo, param);
	}

	public int getTotalCount(Map<String, Object> param) {
		return articleDao.getTotalCount(param);
	}

	public void upViewCount(Map<String, Object> param) {
		articleDao.upViewCount(param);		
	}

	public List<ArticleFile> getArticleFiles(Map<String, Object> param) {
		return articleDao.getArticleFiles(param);
	}

	public void addArticleFiles(List<MultipartFile> files, List<String> inputType, List<String> inputType2, Object articleId) throws IOException {
		List<String[]> fileNames = uploadFiles(files);
		
		if(fileNames != null && fileNames.size() > 0) {
			for(int i = 0; i < fileNames.size(); i++) {
				articleDao.addArticleFiles((fileNames.get(i))[0], (fileNames.get(i))[1],
				inputType.get(i),inputType2.get(i),articleId);
			}
		}
	}

	public ArticleFile getOneFile(int id) {
		return articleDao.getOneFile(id);
	}

	public Map<String, Object> modifyArticle(Map<String, Object> param, List<MultipartFile> files,
			List<Integer> deleteFiles, List<MultipartFile> modifyFiles, List<Integer> modifyFilesId,
			List<String> inputType, List<String> inputType2, List<String> modifyInputType, Object memberId) throws IOException {
		
		Article article = articleDao.getOneArticle(param);
		String msg = "";
		String resultCode = "";
		
		if(article == null) {
			msg = "존재하지 않는 게시물 입니다.";
			resultCode = "F-3";
			
			return Maps.of("msg", msg, "resultCode", resultCode); 
		}
		
		
		if(article.getMemberId() == (long)memberId) {
			List<String[]> fileNames = uploadFiles(files);
			List<String[]> modifyFileNames = uploadFiles(modifyFiles);
			
			List<Integer> modifyFileIds = new ArrayList<>();
			List<String> modifyInputType2 = new ArrayList<>();
			
			if(modifyFilesId != null) {
				for(int i = 0; i < modifyFilesId.size(); i++) {
					if(!modifyFiles.get(i).getOriginalFilename().equals("")) {
						modifyFileIds.add(modifyFilesId.get(i));
						modifyInputType2.add(modifyInputType.get(i));
					}
				}
			}
			
			if(deleteFiles != null && deleteFiles.size() > 0) {
				List<ArticleFile> deleteList = articleDao.getFiles(deleteFiles);
				
				if(deleteList != null) {
					for(ArticleFile file : deleteList) {
						File target = new File(filePath, file.getPrefix() + file.getOriginFileName());
						if(target.exists()) {
							target.delete();
						}
					}
					articleDao.deleteFiles(deleteFiles);
				}
			}
			
			if(fileNames.size() > 0) {
				for(int i = 0; i < fileNames.size(); i++) {
					articleDao.addArticleFiles((fileNames.get(i))[0], (fileNames.get(i))[1],
							inputType.get(i), inputType2.get(i), article.getId());
				}
			}
			
			if(modifyFileIds != null && modifyFileIds.size() > 0) {
				List<ArticleFile> deleteList = articleDao.getFiles(modifyFileIds);
				
				if(deleteList != null) {
					for(ArticleFile file : deleteList) {
						File target = new File(filePath, file.getPrefix() + file.getOriginFileName());
						if(target.exists()) {
							target.delete();
						}
					}
				}
				
				if(modifyFileNames.size() > 0) {
					for(int i = 0; i < modifyFileIds.size(); i++) {
						articleDao.modifyArticleFiles(modifyFileNames.get(i), modifyFileIds.get(i), modifyInputType2.get(i));
					}
				}
			}
			
			articleDao.modifyArticle(param);
			
			msg = "업데이트 했습니다.";
			resultCode = "S-3";
			
			return Maps.of("msg", msg, "resultCode", resultCode);
		} else {
			msg = "권한이 없습니다.";
			resultCode = "F-3";
			
			return Maps.of("msg", msg, "resultCode", resultCode);
		}
		
	}
	
	private List<String[]> uploadFiles(List<MultipartFile> files) throws IOException {
		List<String[]> rs = new ArrayList<>();
		for(MultipartFile file : files) {
			if(!file.getOriginalFilename().equals("")) {
				String[] name = new String[2];
				UUID uid = UUID.randomUUID();
				
				name[0] = uid.toString() + "-";
				name[1] = file.getOriginalFilename();
				
				File target = new File(filePath, name[0] + name[1]);
				
				FileOutputStream fos = new FileOutputStream(target);
				fos.write(file.getBytes());
				fos.close();
				
				rs.add(name);
			}
		}
		
		return rs;
	}
	
	public void like_cnt_up(int articleId) {
		articleDao.like_cnt_up(articleId);
	}
	
	public void like_cnt_down(int articleId) {
		articleDao.like_cnt_down(articleId);
	}
}
