package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class View {

	public View(Map<String, Object> viewsMap) {
		this.viewCount = (int)viewsMap.get("viewCount");
		this.viewArticleId = (int)viewsMap.get("viewArticleId");
	}
	public int viewCount;
	public int viewArticleId;
}