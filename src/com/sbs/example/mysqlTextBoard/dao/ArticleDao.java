package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.dto.View;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class ArticleDao {

	public ArticleDao() {

	}

	public int boardAdd(String name, String code) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO board");
		sql.append("SET name = ?,", name);
		sql.append("code = ?", code);

		return MysqlUtil.insert(sql);
	}

	public Board getBoardById(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	public int add(int boardId, String title, String body, int memberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("body = ?,", body);
		sql.append("boardId = ?,", boardId);
		sql.append("memberId = ?", memberId);

		return MysqlUtil.insert(sql);
	}

	public List<Article> getBoardArticlesForPrint(int boardId) {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("WHERE article.boardId = ?", boardId);

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articlesMap : articlesMapList) {
			Article article = new Article(articlesMap);

			articles.add(article);

		}
		// Collections.reverse(articles);
		return articles;
	}

	public Article getArticleById(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("WHERE article.id = ?", inputedId);

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (articleMap.isEmpty()) {
			return null;
		}

		return new Article(articleMap);
	}

	public void articleModify(Map<String, Object> modifyArgs) {
		SecSql sql = new SecSql();

		int id = (int) modifyArgs.get("id");
		String title = modifyArgs.get("title") != null ? (String) modifyArgs.get("title") : null;
		String body = modifyArgs.get("body") != null ? (String) modifyArgs.get("body") : null;
		int likesCount = modifyArgs.get("likesCount") != null ? (int) modifyArgs.get("likesCount") : -1;
		int commentsCount = modifyArgs.get("commentsCount") != null ? (int) modifyArgs.get("commentsCount") : -1;

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW(),");
		if (title != null) {
			sql.append("title = ?,", title);
		}
		if (body != null) {
			sql.append("body = ?,", body);
		}
		if (likesCount != -1) {
			sql.append("likesCount = ?,", likesCount);
		}
		if (commentsCount != -1) {
			sql.append("commentsCount = ?", commentsCount);
		}

		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	public void articleDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	public int replyAdd(int articleId, String replyBody, int replyMemberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO reply");
		sql.append("SET regDate = NOW(),");
		sql.append("replyBody = ?,", replyBody);
		sql.append("replyArticleId = ?,", articleId);
		sql.append("replyMemberId = ?", replyMemberId);

		return MysqlUtil.insert(sql);
	}

	public Reply getReply(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM reply");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> replyMap = MysqlUtil.selectRow(sql);

		if (replyMap.isEmpty()) {
			return null;
		}

		return new Reply(replyMap);
	}

	public void replyModify(int id, String replyBody) {
		SecSql sql = new SecSql();

		sql.append("UPDATE reply");
		sql.append("SET");
		sql.append("replyBody = ?", replyBody);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	public void replyDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM reply");
		sql.append("WHERE id = ?", id);

		MysqlUtil.delete(sql);
	}

	public List<Reply> getRepliesForPrint(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT reply.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM reply");
		sql.append("INNER JOIN member");
		sql.append("ON reply.replyMemberId = member.id");
		sql.append("WHERE reply.replyArticleId = ?", articleId);

		List<Reply> replise = new ArrayList<>();
		List<Map<String, Object>> repliseMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> repliseMap : repliseMapList) {
			Reply reply = new Reply(repliseMap);

			replise.add(reply);
		}

		return replise;
	}

	public int recommandAdd(int articleId, int recommandMemberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO recommand");
		sql.append("SET regDate = NOW(),");
		sql.append("recommandArticleId = ?,", articleId);
		sql.append("recommandMemberId = ?", recommandMemberId);

		return MysqlUtil.insert(sql);
	}

	public Recommand getRecommand(int articleId, int recommandMemberId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM recommand");
		sql.append("WHERE recommandArticleId = ?", articleId);
		sql.append("AND");
		sql.append("recommandMemberId = ?", recommandMemberId);

		Map<String, Object> recomandMap = MysqlUtil.selectRow(sql);

		if (recomandMap.isEmpty()) {
			return null;
		}

		return new Recommand(recomandMap);
	}

	public void recomandDelete(int articleId, int recommandMemberId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM recommand");
		sql.append("WHERE recommandArticleId = ?", articleId);
		sql.append("AND");
		sql.append("recommandMemberId = ?", recommandMemberId);

		MysqlUtil.delete(sql);

	}

	public List<Recommand> getRecommands(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM recommand");
		sql.append("WHERE recommandArticleId = ?", articleId);

		List<Recommand> recommands = new ArrayList<>();
		List<Map<String, Object>> recommandsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> recommandsMap : recommandsMapList) {
			Recommand recommand = new Recommand(recommandsMap);

			recommands.add(recommand);
		}

		return recommands;
	}

	public void addView(int articleId) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO view");
		sql.append("SET");
		sql.append("viewArticleId = ?", articleId);

		MysqlUtil.insert(sql);

	}

	public List<View> getViews(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM view");
		sql.append("WHERE viewArticleId = ?", articleId);

		List<View> views = new ArrayList<>();
		List<Map<String, Object>> viewsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> viewsMap : viewsMapList) {
			View view = new View(viewsMap);

			views.add(view);
		}

		return views;
	}

	public List<Board> getBoards() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM board");

		List<Board> boards = new ArrayList<>();
		List<Map<String, Object>> boardsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> boardsMap : boardsMapList) {
			Board board = new Board(boardsMap);

			boards.add(board);
		}

		return boards;

	}

	public Board getBoardByCode(String code) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board");
		sql.append("WHERE code = ?", code);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	public List<Article> articles() {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articlesMap : articlesMapList) {
			Article article = new Article(articlesMap);

			articles.add(article);
		}

		return articles;
	}

	public void updatePageHits() {
		SecSql sql = new SecSql();

		sql.append("UPDATE article AS AR ");
		sql.append("INNER JOIN ( ");
		sql.append(
				"SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId, ");
		sql.append("hit ");
		sql.append("FROM ( ");
		sql.append("SELECT  ");
		sql.append("IF( ");
		sql.append("INSTR(GA4_PP.pagePath, '?') = 0, ");
		sql.append("GA4_PP.pagePath, ");
		sql.append("SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1) ");
		sql.append(") AS pagePathWoQueryStr, ");
		sql.append("SUM(GA4_PP.hit) AS hit ");
		sql.append("FROM ga4DataPagePath AS GA4_PP ");
		sql.append("WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%' ");
		sql.append("GROUP BY pagePathWoQueryStr ");
		sql.append(") AS GA4_PP ");
		sql.append(") AS GA4_PP ");
		sql.append("ON AR.id = GA4_PP.articleId ");
		sql.append("SET AR.hitCount = GA4_PP.hit; ");

		MysqlUtil.update(sql);

	}

	// 전체 게시판 수 가져오기
	public int getBoardCount() {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(id) ");
		sql.append("FROM `board`");

		int bodyCount = MysqlUtil.selectRowIntValue(sql);

		return bodyCount;
	}

	// 전체 게시물 수 가져오기
	public int getArticleCount() {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(id) ");
		sql.append("FROM `article`");

		int articleCount = MysqlUtil.selectRowIntValue(sql);

		return articleCount;
	}

	public List<Article> getArticlesExceptNotice() {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("WHERE NOT article.boardId = 1");

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articlesMap : articlesMapList) {
			Article article = new Article(articlesMap);

			articles.add(article);

		}
		// Collections.reverse(articles);
		return articles;
	}

	public String getArticleTagsByArticleId(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT");
		sql.append("IFNULL(GROUP_CONCAT(T.body), '없음') AS tags");
		sql.append("FROM article AS A");
		sql.append("LEFT JOIN tag AS T");
		sql.append("ON A.id = T.relId");
		sql.append("AND T.relTypeCode = 'article'");
		sql.append("WHERE A.id = ?", articleId);
		sql.append("GROUP BY A.id");

		return MysqlUtil.selectRowStringValue(sql);
	}

	public List<Article> getForPrintArticlesByTag(String tagBody) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra_memberName");
		sql.append(", B.name AS extra_boardName");
		sql.append(", B.code AS extra_boardCode");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("INNER JOIN `tag` AS T");
		sql.append("ON T.relTypeCode = 'article'");
		sql.append("AND A.id = T.relId");
		sql.append("WHERE T.body = ?", tagBody);
		sql.append("ORDER BY A.id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}
}