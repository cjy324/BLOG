package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Recommand {

	public Recommand(Map<String, Object> recomandMap) {
		this.id = (int) recomandMap.get("id");
		this.regDate = (String) recomandMap.get("regDate");
		this.recommandArticleId = (int) recomandMap.get("recommandArticleId");
		this.recommandMemberId = (int) recomandMap.get("recommandMemberId");
	}

	public int id;
	public String regDate;
	public int recommandArticleId;
	public int recommandMemberId;

}