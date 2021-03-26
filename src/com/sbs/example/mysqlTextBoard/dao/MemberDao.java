package com.sbs.example.mysqlTextBoard.dao;

import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class MemberDao {

	public MemberDao() {

	}

	// 회원가입
	public int join(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO member");
		sql.append("SET loginId = ?,", loginId);
		sql.append("loginPw = ?,", loginPw);
		sql.append("name = ?", name);

		return MysqlUtil.insert(sql);
	}

	// 로그인아이디로 회원정보 가져오기
	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM member");
		sql.append("WHERE loginId = ?", loginId);
		
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		
		return new Member(memberMap);
	}

	// 회원번호로 회원정보 가져오기
	public Member getMemberById(int loginMemberId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM member");
		sql.append("WHERE id = ?", loginMemberId);
		
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		
		return new Member(memberMap);
	}
	
	//사이트 방문자 수 가져오기(구글애널리틱스 API로 정보 가져오기)
	public int getVisitorCount() {
		SecSql sql = new SecSql();

		sql.append("SELECT hit ");
		sql.append("FROM ga4DataPagePath AS GA4_PP ");
		sql.append("WHERE GA4_PP.pagePath NOT LIKE '/?%' ");
		sql.append("AND GA4_PP.pagePath NOT LIKE'%.html'");

		int visitorCount = MysqlUtil.selectRowIntValue(sql);

		return visitorCount;
	}

}