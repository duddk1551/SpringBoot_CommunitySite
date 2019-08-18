package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.PageInfo;
import com.example.demo.PageMaker;
import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleFile;
import com.example.demo.dto.ArticleReply;
import com.example.demo.dto.Like;
import com.example.demo.service.ArticleReplyService;
import com.example.demo.service.ArticleService;
import com.example.demo.service.LikeService;
import com.example.demo.service.MemberService;


@Controller
public class ArticleController {
	
	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleReplyService articleReplyService;
	@Autowired
	MemberService memberService;
	@Autowired
	LikeService likeService;
	@Value("${custom.uploadDir}")
	private String filePath;
	
	@RequestMapping("article/list")
	public String showList(@RequestParam Map<String,Object> param, @RequestParam(value="boardId", defaultValue = "0") int boardId, PageInfo pInfo, Model model) {
		
		if(boardId <= 0) {
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		param.put("boardId", boardId);
		//댓글 개수 가져오기
		param.put("extra__replyCount", true);
		
		
		List<Article> list = articleService.getArticles(pInfo, param);
		
		PageMaker maker = new PageMaker();
		maker.setpInfo(pInfo);
		maker.setTotalCount(articleService.getTotalCount(param));
		
		model.addAttribute("list", list);
		model.addAttribute("maker", maker);
		
		return "article/list";
	}

	@RequestMapping("article/add")
	public String showAdd(@RequestParam(value="boardId", defaultValue="0")int boardId, HttpSession session, Model model) {
		
		long loginedMemberId = (long)session.getAttribute("loginedMemberId");
		
		long memberLevel = memberService.getMemberLevel(loginedMemberId);
		
		if(boardId == 2 && memberLevel != 0) {
			model.addAttribute("alertMsg", "관리자만 접근 가능합니다.");
			model.addAttribute("locationReplaceUrl", "/article/list?boardId="+ boardId +"&cPage=1");
			
			return "common/redirect";
		} else if(boardId > 1 && memberLevel < 2){
			model.addAttribute("alertMsg", "우수회원 이상만 글쓰기가 가능합니다.");
			model.addAttribute("locationReplaceUrl", "/article/list?boardId="+ boardId +"&cPage=1");
			
			return "common/redirect";
		} else {
			return "article/add";
		}
		
	}
	
	@RequestMapping("article/doAdd")
	public String doAdd(Model model,  HttpSession session,
			@RequestParam Map<String, Object> param,
			@RequestParam(value = "files") List<MultipartFile> files,
			@RequestParam(value = "type", required = false) List<String> inputType,
			@RequestParam(value = "type2", required = false) List<String> inputType2) {
		
		
		String redirectUrl = (String)param.get("redirectUrl");
		String msg = "";
		
		long boardId = Long.parseLong((String) param.get("boardId"));
		long memberLevel = memberService.getMemberLevel((long)session.getAttribute("loginedMemberId"));
		
		
		try {
			long newId = (long)session.getAttribute("loginedMemberId");
			param.put("memberId", newId);
			
			articleService.add(param);
			articleService.addArticleFiles(files, inputType, inputType2, param.get("id"));
			
			if(redirectUrl == null || redirectUrl.length() == 0) {
				redirectUrl = "/article/detail?id=" + param.get("id") + "&boardId=" + param.get("boardId") + "&cPage=1";
			}
			model.addAttribute("locationReplaceUrl", redirectUrl);
			msg = "작성 완료";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "작성 실패";
			model.addAttribute("historyBack", true);
		}
		model.addAttribute("alertMsg", msg);
	
		return "common/redirect";
	}
	
	@RequestMapping("article/detail")
	public String showDetail(Model model, @RequestParam Map<String,Object> param, HttpSession session) {
		
		long memberLevel = memberService.getMemberLevel((long)session.getAttribute("loginedMemberId"));
		long boardId = Long.parseLong((String) param.get("boardId"));
		
		if(boardId == 3 && memberLevel == 1) {
			model.addAttribute("alertMsg", "우수회원 이상만 글읽기가 가능합니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		} else {
		
		articleService.upViewCount(param);
		Article article = articleService.getOne(param);
		List<ArticleFile> files = articleService.getArticleFiles(param);

		HashMap<String,Object> like = new HashMap<String,Object>();
		
		like.put("id", article.getId());
		like.put("memberId", (long)session.getAttribute("loginedMemberId"));
		
		int like_check = likeService.getLike(like);
		
		if(like_check == 0) {
			model.addAttribute("likeAlready", false);
		} else {
			model.addAttribute("likeAlready", true);
		}
		
		model.addAttribute("article", article);
		model.addAttribute("files", files);
		
		return "article/detail";
		
		}
	}
	
	@RequestMapping("article/showImg")
	@ResponseBody
	public ResponseEntity<Resource> showImg(int id, HttpServletResponse response) throws IOException {

		ArticleFile articleFile = articleService.getOneFile(id);
		HttpHeaders header = new HttpHeaders();
		File target = new File(filePath, articleFile.getPrefix() + articleFile.getOriginFileName());
		Resource rs = null;
		String mimeType = null;

		if (target.exists()) {
			rs = new UrlResource(target.toURI());
			mimeType = Files.probeContentType(Paths.get(rs.getFilename()));
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			header.setContentType(MediaType.parseMediaType(mimeType));

		}
		return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
	}
	
	@RequestMapping("article/downloadFile")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(int id, Model model) throws IOException {
		ArticleFile articleFile = articleService.getOneFile(id);
		
		File target = new File(filePath, articleFile.getPrefix() + articleFile.getOriginFileName());
		Resource rs = null;
		String mimeType = null;
		
		if(target.exists()) {
			rs = new UrlResource(target.toURI());
			mimeType = Files.probeContentType(Paths.get(rs.getFilename()));
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + rs.getFilename() + "\"")
					.body(rs);
		}
		return null;
	}
	
	@RequestMapping("article/modify")
	public String showModify(Model model, HttpSession session, @RequestParam Map<String,Object> param) {
		long loginedId = (long)session.getAttribute("loginedMemberId");
		Article article = articleService.getOne(param);
		long memberLevel = memberService.getMemberLevel(loginedId);
		
		if(loginedId == article.getMemberId()) {
			List<ArticleFile> files = articleService.getArticleFiles(param);
			
			model.addAttribute("article", article);
			if(files != null && files.size() > 0) {
				model.addAttribute("files", files);
			}
			
			return "article/modify";
		} else {
			model.addAttribute("alertMsg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}

	}
	
	@RequestMapping("article/doModify")
	public String doModify(@RequestParam Map<String,Object> param,
			@RequestParam(value = "files") List<MultipartFile> files,
			@RequestParam(value = "deleteFiles", required = false) List<Integer> deleteFiles,
			@RequestParam(value = "modifyFilesId", required = false) List<Integer> modifyFilesId,
			@RequestParam(value = "modifyFiles") List<MultipartFile> modifyFiles,
			@RequestParam(value = "type", required = false) List<String> inputType,
			@RequestParam(value = "type2", required = false) List<String> inputType2,
			@RequestParam(value = "modifyType2", required = false) List<String> ModifyInputType,
			HttpSession session, Model model) throws IOException {
		
		param.put("memberLevel", memberService.getMemberLevel((long)session.getAttribute("loginedMemberId")));
		
		Map<String,Object> rs = articleService.modifyArticle(param, files, deleteFiles, modifyFiles, modifyFilesId, 
				inputType, inputType2, ModifyInputType, session.getAttribute("loginedMemberId"));
		
		String msg = (String) rs.get("msg");
		String resultCode = (String) rs.get("resultCode");
		
		model.addAttribute("alertMsg", msg);
		
		if(resultCode.startsWith("S-")) {
			String redirectUrl = "/article/detail?id=" + param.get("id") + "&boardId=" + param.get("boardId") + "&cPage=1";
			model.addAttribute("locationReplaceUrl", redirectUrl);
		} else {
			model.addAttribute("historyBack", true);
		}
		
		return "common/redirect";
	}
	
	@RequestMapping("article/deleteArticle")
	public String deleteArticle(@RequestParam Map<String,Object> param, HttpSession session, Model model) {
		
		long memberLevel = memberService.getMemberLevel((long)session.getAttribute("loginedMemberId"));
		param.put("memberLevel", memberLevel);
		Map<String,Object> rs = articleService.deleteArticle(param, session.getAttribute("loginedMemberId"));
		
		String msg = (String) rs.get("msg"); 
		String resultCode = (String) rs.get("resultCode");
		String redirectUrl = "";
		
		model.addAttribute("alertMsg", msg);
		
		if(resultCode.startsWith("S-")) {
			redirectUrl = "/article/list?boardId=" + param.get("boardId");
			model.addAttribute("locationReplaceUrl", redirectUrl);
		} else {
			model.addAttribute("historyBack", true);
		}
		
		return "common/redirect";
	}
	
	
	@RequestMapping("article/addReply")
	@ResponseBody
	public Map<String, Object> addReply(@RequestParam Map<String,Object> param, Model model, HttpSession session) {
		
		param.put("memberId", session.getAttribute("loginedMemberId"));
		
		articleReplyService.addReply(param);
		
		return Maps.of("successCode", "S-1");
	}
	
	@RequestMapping("article/getAllReplies")
	@ResponseBody
	public List<ArticleReply> getAllReplies(@RequestParam Map<String, Object> param) {
		
		List<ArticleReply> replies = articleReplyService.getArticleReplies(param);
		
		return replies;
	}
	
	@RequestMapping("article/deleteReply")
	@ResponseBody
	public Map<String, Object> deleteReply(@RequestParam Map<String,Object> param, HttpSession session) {
		
		long loginedId = (long) session.getAttribute("loginedMemberId");
		param.put("loginedMemberId", loginedId);
		Map<String,Object> rs = articleReplyService.deleteReply(param);
		
		String msg = (String)rs.get("msg");
		String resultCode = (String)rs.get("resultCode");
		
		if(!resultCode.startsWith("S-")) {
			return Maps.of("msg", msg, "success", true);
		} else {
			return Maps.of("msg", msg, "success", false);
		}
	}
	
	@RequestMapping("article/modifyReply")
	@ResponseBody
	public Map<String, Object> modifyReply(@RequestParam Map<String,Object> param, HttpSession session) {
		
		long memberId = (long) session.getAttribute("loginedMemberId");
		param.put("loginedMemberId", memberId);
		Map<String,Object> rs = articleReplyService.modifyReply(param);
		
		String msg = (String)rs.get("msg");
		String resultCode = (String)rs.get("resultCode");
		
		return Maps.of("msg", msg);
	}
	
	@RequestMapping("article/like")
	@ResponseBody
	public Map<String,Object> like(int articleId, HttpSession session) {
		long memberId = (long)session.getAttribute("loginedMemberId");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		HashMap <String,Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", articleId);
		hashMap.put("memberId", memberId);
		int like_check = likeService.getLike(hashMap);
		Article article = articleService.getOne(hashMap);
		long like_cnt = article.getLikes();
		System.out.println("좋아요 갯수  :" + like_cnt);
		
		if(like_check == 0) {
			likeService.addLike(hashMap);
			result.put("msg", "좋아요");
			like_cnt++;
			articleService.like_cnt_up(articleId);
		} else {
			likeService.cancelLike(hashMap);
			result.put("msg", "좋아요 취소");
			like_cnt--;
			articleService.like_cnt_down(articleId);
		}
		
		if(like_cnt < 0) {
			like_cnt = 0;
		}
		result.put("like_check", like_check);
		result.put("like_cnt", like_cnt);
		    
		return result;
	}
	
}