package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberService {

	MemberDao memberDao;

	public MemberService() {

		memberDao = Container.memberDao;
	}

	public int join(String loginId, String loginPw, String name) {

		return memberDao.join(loginId, loginPw, name);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int loginMemberId) {
		return memberDao.getMemberById(loginMemberId);
	}

}
