package com.example.demo.service;

import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.TempKey;
import com.example.demo.dao.MemberDao;
import com.example.demo.dto.Member;
import com.example.demo.handler.MailHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao mdo;
	@Autowired
	private JavaMailSender sender;

	public Map<String, Object> login(Map<String, Object> param) {
		
		param.put("where__checkDelMember", true);
		
		Member loginedMember = (Member) mdo.getMatchedOne(param);
		
		String msg = "";
		String resultCode = "";

		long loginedMemberId = 0;

		if (loginedMember == null) {
			resultCode = "F-1";
			msg = "일치하는 회원이 없습니다.";

			return Maps.of("resultCode", resultCode, "msg", msg);
		} else {
			loginedMemberId = loginedMember.getId();

			resultCode = "S-1";
			msg = "로그인 되었습니다.";

			return Maps.of("resultCode", resultCode, "msg", msg, "loginedMemberId", loginedMemberId);

		}
	}


	public Member getOne(long loginedMemberId) {
		return mdo.getOne(loginedMemberId);
	}

	
	public Map<String, Object> doubleCheck(Map<String, Object> param) {
		
		//회원가입시 중복되는 아이디와 이메일 확인해주는 메서드
		int count = mdo.doubleCheck(param);
		String msg = "";
		String resultCode = "";
		
		if(count <= 0) {
			param.put("authKey", new TempKey(30).getKey());
			mdo.addMember(param);
			
			MailHandler mail;
			try {
				mail = new MailHandler(sender);
				mail.setFrom("js_WEB", "zzory");
				mail.setTo((String)param.get("email"));
				mail.setSubject("회원 인증 메일");
				mail.setText(new StringBuffer().append("<h1>이메일이증 메일</h1>")
						.append("<a href='http://localhost:8083/member/confirm?email=").append((String)param.get("email"))
						.append("&authKey=").append((String)param.get("authKey"))
						.append("' target='_blank'> 누르시면 메일 인증 페이지로 이동합니다. </a>").toString()
						);
				mail.send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			msg = "회원가입 성공 !";
			resultCode = "S-2";
		} else {
			msg = "회원가입 실패..";
			resultCode = "F-2";
		}
		return Maps.of("msg", msg, "resultCode", resultCode);
	}
	
	//이메일 인증 상태를 설정해주는 메서드
	public Map<String, Object> updateAuthStatus(Map<String, Object> param) {
		Member member = mdo.getEmailMember(param);
		String msg = "";
		String resultCode = "";
		if(member == null) {
			msg = "이메일 인증 실패";
			resultCode = "F-5";
		} else {
			mdo.updateAuthStatus(param);
			msg = "이메일 인증 성공";
			resultCode = "S-5";
		}
		return Maps.of("msg", msg, "resultCode", resultCode);
	}

	public Map<String, Object> updateMember(Map<String, Object> param) {
		
		int checkedCount = mdo.checkedPw((String)param.get("loginPw"));
		
		String msg = "";
		String resultCode = "";
		if(checkedCount == 0) {
			msg = "기존 비밀번호를 바르게 입력해주세요.";
			resultCode = "F-5";
		} else {
			if(param.get("newPw") != null) {
				param.put("exclude__pwData", true);
			} 
			mdo.updateMember(param);
			msg = "회원정보 수정 성공";
			resultCode = "S-5";
		}
		return Maps.of("msg", msg, "resultCode", resultCode);
	}

	public Map<String, Object> checkedStatus(Map<String, Object> param) {
		
		Member member = mdo.getMatchedOne(param);
		
		String status = "";
		
		if(member == null) {
			status = "checkLoginInfo";
		} else {	
			boolean memberStatus = member.isEmailAuthStatus();
			if(memberStatus == false) {
				status = "needToCertification";
			} 
		}
		return Maps.of("status", status);
	}

	public void delMember(long id) {
		mdo.delMember(id);
	}

	public Map<String, Object> findLoginId(Map<String, Object> param) {
		Member member = mdo.findMemberInfo(param);
		
		String msg = "";
		String resultCode = "";
		if(member == null) {
			msg = "회원정보가 존재하지 않습니다.";
			resultCode = "F-5";
		} else {
			
			MailHandler mail;
			try {
				mail = new MailHandler(sender);
				mail.setFrom("js_WEB", "조조갈");
				mail.setTo((String)param.get("email"));
				mail.setSubject("아이디를 확인해주세요.");
				mail.setText(new StringBuffer().append("<h1>아이디 찾기 메일</h1>")
						.append("귀하의 아이디는 : ").append((String)member.getLoginId())
						.append("입니다.").toString()
						);
				mail.send();
			} catch (Exception e) {
				e.printStackTrace();
			}

			msg = "아이디를 이메일로 발송했습니다.";
			resultCode = "S-5";
		}
		return Maps.of("msg", msg, "resultCode", resultCode);
	}
	
	public Map<String, Object> findLoginPw(Map<String, Object> param) {
		
		Member member = mdo.findMemberInfo(param);
		
		String msg = "";
		String resultCode = "";
		if(member == null) {
			msg = "회원정보가 존재하지 않습니다.";
			resultCode = "F-5";
		} else {
			param.put("newPw", new TempKey(5).getKey());
			param.put("id", member.getId());
			
			MailHandler mail;
			try {
				mail = new MailHandler(sender);
				mail.setFrom("js_WEB", "조조갈");
				mail.setTo((String)param.get("email"));
				mail.setSubject("비밀번호를 확인해주세요.");
				mail.setText(new StringBuffer().append("<h1>비밀번호 찾기 메일</h1>")
						.append("귀하의 변경된 비밀번호는 : ").append((String)param.get("newPw"))
						.append("입니다.").toString()
						);
				mail.send();
				mdo.modifyNewPw(param);
			} catch (Exception e) {
				e.printStackTrace();
			}

			msg = "변경된 비밀번호를 이메일로 발송했습니다.";
			resultCode = "S-5";
		}
		return Maps.of("msg", msg, "resultCode", resultCode);
	}


	public long getMemberLevel(long loginedMemberId) {
		
		Member member = mdo.getOne(loginedMemberId);
		
		long memberLevel = member.getMemberLevel();
		
		return memberLevel;
	}

}