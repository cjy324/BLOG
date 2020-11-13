package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberDao {

	List<Member> members;
	Connection con;
	String driver;
	String url;
	String userId;
	String userPw;
	String sql;

	public MemberDao() {
		members = new ArrayList<>();
		driver = "com.mysql.cj.jdbc.Driver";
		con = null;
		url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
		userId = "sbsst";
		userPw = "sbs123414";
		sql = "";
	}

	// 회원가입
	public int join(String createId, String createPw, String name) {

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con = DriverManager.getConnection(url, userId, userPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			sql = "INSERT INTO `member` " + "SET " + "loginId = " + "'" + createId + "', " + "loginPw = " + "'"
					+ createPw + "', " + "`name` = " + "'" + name + "'";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	// 아이디 중복 체크
	public Member checkEqualsLogindId(String createId) {
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con = DriverManager.getConnection(url, userId, userPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			sql = "SELECT * FROM `member`";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs;
				rs = pstmt.executeQuery();

				while (rs.next()) {
					if (rs.getString("loginId").equals(createId)) {
						Member member = new Member();
						member.loginId = createId;
						return member;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	// 로그인 아이디 체크
	public Member getEqualsLogindId(String loginId) {
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con = DriverManager.getConnection(url, userId, userPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			sql = "SELECT * FROM `member`";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs;
				rs = pstmt.executeQuery();

				while (rs.next()) {
					if (rs.getString("loginId").equals(loginId)) {

						Member member = new Member();
						member.memberId = rs.getInt("memberId");
						member.loginId = rs.getString("loginId");
						member.loginPw = rs.getString("loginPw");
						member.name = rs.getString("name");

						return member;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	// 회원번호로 회원정보 가져오기
	public Member getEqualsMemberByMemberId(int loginedMemberId) {
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con = DriverManager.getConnection(url, userId, userPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			sql = "SELECT * FROM `member`";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs;
				rs = pstmt.executeQuery();

				while (rs.next()) {
					if (rs.getInt("memberId") == loginedMemberId) {

						Member member = new Member();
						member.memberId = rs.getInt("memberId");
						member.loginId = rs.getString("loginId");
						member.loginPw = rs.getString("loginPw");
						member.name = rs.getString("name");

						return member;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
