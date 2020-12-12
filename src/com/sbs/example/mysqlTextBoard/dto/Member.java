package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Member {

	public Member(Map<String, Object> memberMap) {
		this.id = (int)memberMap.get("id");
		this.loginId = (String)memberMap.get("loginId");
		this.loginPw = (String)memberMap.get("loginPw");
		this.name = (String)memberMap.get("name");
	
	}

	public int id;
	public String loginId;
	public String loginPw;
	public String name;

	public boolean isAdmin() {
		return loginId.equals("asd");
	}

	public String memberType() {
		return isAdmin() ? "관리자" : "일반 회원";
	}
	
}
