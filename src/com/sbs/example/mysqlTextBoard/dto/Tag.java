package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Tag {

	public Tag(Map<String, Object> tagsMap) {
		this.id = (int) tagsMap.get("id");
		this.regDate = (String) tagsMap.get("regDate");
		this.updateDate = (String) tagsMap.get("updateDate");
		this.relTypeCode = (String) tagsMap.get("relTypeCode");
		this.relId = (int) tagsMap.get("relId");
		this.body = (String) tagsMap.get("body");

	}

	public Tag() {

	}

	private int id;
	private String regDate;
	private String updateDate;
	private String relTypeCode;
	private int relId;
	private String body;

	
	
	
}
