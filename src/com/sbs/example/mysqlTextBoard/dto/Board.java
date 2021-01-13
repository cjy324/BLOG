package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Board {
	
	public Board(Map<String, Object> boardMap) {
		this.id = (int)boardMap.get("id");
		this.name = (String)boardMap.get("name");
		this.code = (String)boardMap.get("code");
	}
	private int id;
	private String name;
	private String code;


}