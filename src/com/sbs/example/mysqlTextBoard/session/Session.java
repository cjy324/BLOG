package com.sbs.example.mysqlTextBoard.session;

public class Session {

	public int loginedMemberId;
	public int selectedBoardId;

	public boolean loginStatus() {
		return loginedMemberId != 0;
	}
	
	

}
