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
public class ArticleReply {
	private long id;
	private String regDate;
	private long articleId;
	private long boardId;
	private long memberId;
	private String body;
	private Map<String, Object> extra;
}
