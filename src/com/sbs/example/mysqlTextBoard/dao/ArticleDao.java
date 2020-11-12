package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {

	Connection con;
	String driver;
	String url;
	String userId;
	String userPw;
	String sql;

	public ArticleDao() {

		driver = "com.mysql.cj.jdbc.Driver";
		con = null;
		url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
		// jdbc 국제표준에 의한 통신을 할 것이다.
		// 대상은 mysql로 간주한다.
		// 127.0.0.1:3306 주소
		// textBoard 연결 DB명
		//
		// 여기부터는 옵션사항
		// useUnicode=true&characterEncoding=utf8 컴퓨터상의 표준어인 UTF8을 사용할 것이다.
		// autoReconnect=true 잠시 연결이 끊어지면 자동으로 다시 연결
		// serverTimezone=Asia/Seoul 시간은 아시아/서울로 설정
		// useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull 몰라도 됨?
		// connectTimeout=60000&socketTimeout=60000 60초 후에는 자동으로 꺼짐
		userId = "sbsst";
		userPw = "sbs123414";
		sql = "";
	}

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		// MySQL 드라이버 등록
		try {
			try {
				Class.forName(driver);
				// driver를 한번 깨우는 것
				// 이전에는 반드시 사전 실행되었어야 했지만
				// 최근에는 자동으로 실행되서 굳이 작성하지 않아도 됨
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(url, userId, userPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			sql = "SELECT * FROM article";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");
					int memberId = rs.getInt("memberId");
					int boardId = rs.getInt("boardId");

					Article article = new Article(id, regDate, updateDate, title, body, memberId, boardId);

					articles.add(article);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			// ClassNotFoundException, SQLException과 같이 미리 예비한 상황 이외의
			// 오류 상황이 발생하면 프로그램은 바로 종료되고 다시 켜지고를 반복
			// 따라서 무조건 실행되는 finally로 con.close();를 감싸준다.
			// 안꺼지는 일이 없도록
			try {
				if (con != null) {
					con.close();
					// 자원해제
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return articles;
	}

	public int add(String title, String body, int memberId, int boardId) {

		try {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(url, userId, userPw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql = "INSERT INTO article "
				+ ""
				+ "SET regDate = NOW(),"
				+ ""
				+ "updateDate = NOW(),"
				+ ""
				+ "title = " + "'" + title + "',"
				+ ""
				+ "`body` = " + "'" + body + "',"
				+ ""
				+ "memberId = " + memberId + ","
				+ ""
				+ "boardId = "+ boardId;
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return 0;
	}

}
