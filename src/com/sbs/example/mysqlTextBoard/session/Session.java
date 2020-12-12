package com.sbs.example.mysqlTextBoard.session;

public class Session {

	public int loginMemberId;
	public int loginAdminMemberId;
	public int selectedBoardId;

	public boolean loginStatus() {
		// TODO Auto-generated method stub
		return loginMemberId > 0;
	}

	public boolean loginAdminMemberStatus() {
		
		return loginAdminMemberId > 0;
	}
	
	public Session() {
		selectedBoardId = 1;
	}

}