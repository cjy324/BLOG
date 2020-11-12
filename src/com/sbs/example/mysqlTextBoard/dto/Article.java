package com.sbs.example.mysqlTextBoard.dto;

public class Article {
	

	public int id;
	public String regDate;
	public String updateDate;
	public String title;
	public String body;
	public int memberId;
	public int boardId;

	public Article(int id, String regDate, String updateDate, String title, String body, int memberId, int boardId) {

		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.memberId = memberId;
		this.boardId = boardId;
	}

}
