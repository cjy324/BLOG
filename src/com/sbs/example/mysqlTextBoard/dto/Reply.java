package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Reply {

	public Reply(Map<String, Object> replyMap) {
		this.id = (int)replyMap.get("id");
		this.regDate = (String)replyMap.get("regDate");
		this.replyBody = (String)replyMap.get("replyBody");
		this.replyArticleId = (int)replyMap.get("replyArticleId");
		this.replyMemberId = (int)replyMap.get("replyMemberId");
		
		if (replyMap.containsKey("extra_memberName")) {
			this.extra_memberName = (String) replyMap.get("extra_memberName");
		}

	}
	public int id;
	public String regDate;
	public String replyBody;
    public int replyArticleId;
    public int replyMemberId;
    public String extra_memberName;
	
	
}
