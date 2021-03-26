package com.sbs.example.mysqlTextBoard.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Project;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.dto.Tag;
import com.sbs.example.mysqlTextBoard.dto.View;

public class ArticleService {

	ArticleDao articleDao;
	TagService tagService;

	public ArticleService() {

		articleDao = Container.articleDao;
		tagService = Container.tagService;
	}

	public int boardAdd(String name, String code) {
		return articleDao.boardAdd(name, code);
	}

	public Board getBoardById(int inputedId) {
		return articleDao.getBoardById(inputedId);
	}

	public int add(int boardId, String title, String body, int memberId) {
		return articleDao.add(boardId, title, body, memberId);
	}

	public List<Article> getBoardArticlesForPrint(int boardId) {
		return articleDao.getBoardArticlesForPrint(boardId);
	}

	public Article getArticleById(int inputedId) {
		return articleDao.getArticleById(inputedId);
	}

	public void articleModify(int id, String title, String body) {

		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", id);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);

		articleDao.articleModify(modifyArgs);

		// articleDao.articleModify(id, title, body);

	}

	public void articleDelete(int id) {
		articleDao.articleDelete(id);

	}

	public int replyAdd(int articleId, String replyBody, int replyMemberId) {
		return articleDao.replyAdd(articleId, replyBody, replyMemberId);
	}

	public Reply getReply(int inputedId) {
		return articleDao.getReply(inputedId);
	}

	public void replyModify(int id, String replyBody) {
		articleDao.replyModify(id, replyBody);

	}

	public void replyDelete(int id) {
		articleDao.replyDelete(id);

	}

	public List<Reply> getRepliesForPrint(int articleId) {
		return articleDao.getRepliesForPrint(articleId);
	}

	public void recommandAdd(int articleId, int recommandMemberId) {
		articleDao.recommandAdd(articleId, recommandMemberId);
	}

	public Recommand getRecommand(int articleId, int recommandMemberId) {
		return articleDao.getRecommand(articleId, recommandMemberId);
	}

	public void recomandDelete(int articleId, int recommandMemberId) {
		articleDao.recomandDelete(articleId, recommandMemberId);

	}

	public List<Recommand> getRecommands(int articleId) {
		return articleDao.getRecommands(articleId);
	}

	public void addView(int articleId) {
		articleDao.addView(articleId);

	}

	public List<View> getViews(int articleId) {
		return articleDao.getViews(articleId);
	}

	public List<Board> getBoards() {
		return articleDao.getBoards();
	}

	public Board getBoardByCode(String code) {
		return articleDao.getBoardByCode(code);
	}

	public List<Article> getArticlesForPrint() {
		return articleDao.articles();
	}

	public List<Article> getBoardArticlesByCodeForPrint(String code) {
		Board board = articleDao.getBoardByCode(code);
		return articleDao.getBoardArticlesForPrint(board.getId());
	}

	public void articleModify(Map<String, Object> modifyArgs) {
		articleDao.articleModify(modifyArgs);

	}

	public void updatePageHits() {
		articleDao.updatePageHits();

	}

	public int getBoardCount() {
		return articleDao.getBoardCount();
	}

	public int getArticleCount() {
		return articleDao.getArticleCount();
	}

	public List<Article> getArticlesExceptNotice() {
		return articleDao.getArticlesExceptNotice();
	}

	public String getArticleTagsByArticleId(int articleId) {
		return articleDao.getArticleTagsByArticleId(articleId);
	}

	public Map<String, List<Article>> getArticlesByTagMap() {

		// 중복이 제거된 태그 body 리스트 먼저 가져오기
		List<String> tagBodies = tagService.getDedupTagBodiesByRelTypeCode("article");

		Map<String, List<Article>> articlesMap = new LinkedHashMap<>();

		for (String tagBody : tagBodies) {
			// tagBody가 달려있는 article 리스트 가져오기
			List<Article> articles = getForPrintArticlesByTag(tagBody);

			// article 리스트 map에 담기
			articlesMap.put(tagBody, articles);

		}

		return articlesMap;
	}

	private List<Article> getForPrintArticlesByTag(String tagBody) {
		return articleDao.getForPrintArticlesByTag(tagBody);
	}

	public List<Project> getProjects() {
		return articleDao.getProjects();
	}

}
