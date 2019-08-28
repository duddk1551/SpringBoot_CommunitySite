package com.example.demo.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
	private long id;
	private long memberId;
	private String writer;
	private String body;
}
