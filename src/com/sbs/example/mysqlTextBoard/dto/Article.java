package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Article {

	public Article(Map<String, Object> articlesMap) {
		this.id = (int) articlesMap.get("id");
		this.regDate = (String) articlesMap.get("regDate");
		this.updateDate = (String) articlesMap.get("updateDate");
		this.title = (String) articlesMap.get("title");
		this.body = (String) articlesMap.get("body");
		this.boardId = (int) articlesMap.get("boardId");
		this.memberId = (int) articlesMap.get("memberId");
		this.likesCount = (int) articlesMap.get("likesCount");
		this.commentsCount = (int) articlesMap.get("commentsCount");
		this.hitCount = (int) articlesMap.get("hitCount");

		if (articlesMap.containsKey("extra_memberName")) {
			this.extra_memberName = (String) articlesMap.get("extra_memberName");
		}
		if (articlesMap.containsKey("extra_boardName")) {
			this.extra_boardName = (String) articlesMap.get("extra_boardName");
		}
		if (articlesMap.containsKey("extra_boardCode")) {
			this.extra_boardCode = (String) articlesMap.get("extra_boardCode");
		}

	}

	public Article() {

	}

	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int boardId;
	private String extra_boardName;
	private String extra_boardCode;
	private int memberId;
	private String extra_memberName;
	private int likesCount;
	private int commentsCount;
	private int hitCount;

}
