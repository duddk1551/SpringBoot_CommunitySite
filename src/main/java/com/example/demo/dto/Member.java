package com.example.demo.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Member {
	private long id;
	private String regDate;
	private String loginId;
	private String loginPw;
	private String name;
	private String email;
	private long memberLevel;
	private String authkey;
	private boolean emailAuthStatus;
	private boolean delStatus;
	private Map<String, String> extra;
}
