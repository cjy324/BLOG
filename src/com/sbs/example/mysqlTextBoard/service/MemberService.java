package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberService {

	MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public int join(String createId, String createPw, String name) {
		return memberDao.join(createId, createPw, name);
	}

	public boolean checkEqualsLogindId(String createId) {
		Member member = memberDao.checkEqualsLogindId(createId);
		if (member == null) {
			return false;
		}

		return true;
	}

	public Member getEqualsLogindId(String loginId) {
		Member member = memberDao.getEqualsLogindId(loginId);
		return member;
	}

	public Member getEqualsMemberByMemberId(int loginedMemberId) {
		Member member = memberDao.getEqualsMemberByMemberId(loginedMemberId);
		return member;
	}

}
