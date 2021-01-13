package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Board {
	
	public Board(Map<String, Object> boardMap) {
		this.id = (int)boardMap.get("id");
		this.name = (String)boardMap.get("name");
		this.code = (String)boardMap.get("code");
	}
	private int id;
	private String name;
	private String code;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}