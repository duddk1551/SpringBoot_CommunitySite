package com.example.demo.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chat {
	private long id;
	private String writer;
	private String body;
}
