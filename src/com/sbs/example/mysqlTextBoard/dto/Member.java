package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Member {

	public Member(Map<String, Object> memberMap) {
		this.id = (int)memberMap.get("id");
		this.loginId = (String)memberMap.get("loginId");
		this.loginPw = (String)memberMap.get("loginPw");
		this.name = (String)memberMap.get("name");
	
	}

	private int id;
	private String loginId;
	private String loginPw;
	private String name;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
	
	public boolean isAdmin() {
		return loginId.equals("asd");
	}

	public String memberType() {
		return isAdmin() ? "관리자" : "일반 회원";
	}
	
}
