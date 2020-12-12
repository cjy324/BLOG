package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Board {
	
	public Board(Map<String, Object> boardMap) {
		this.id = (int)boardMap.get("id");
		this.name = (String)boardMap.get("name");
		this.code = (String)boardMap.get("code");
	}
	public int id;
	public String name;
	public String code;

}