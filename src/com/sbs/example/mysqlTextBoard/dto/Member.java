package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
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
	
	
	public boolean isAdmin() {
		return loginId.equals("asd");
	}

	public String memberType() {
		return isAdmin() ? "관리자" : "일반 회원";
	}
	
}
