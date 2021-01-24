package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Project {

	public Project(Map<String, Object> projectMap) {
		this.id = (int)projectMap.get("id");
		this.name = (String)projectMap.get("name");
		this.code = (String)projectMap.get("code");
	}
	
	private int id;
	private String name;
	private String code;


}