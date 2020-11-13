package com.sbs.example.mysqlTextBoard.service;

import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;

public class ArticleService {

	ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public int add(String title, String body, int memberId, int boardId) {
		return articleDao.add(title, body, memberId, boardId);
	}

	public void deleteArticleById(int inputedId) {
		articleDao.deleteArticleById(inputedId);

	}

	public void modifyArticle(int inputedId, String title, String body) {
		articleDao.modifyArticle(inputedId, title, body);

	}

	public Article getDetailArticleById(int inputedId) {
		return articleDao.getArticleById(inputedId);

	}

	public int boardAdd(String boardName) {
		return articleDao.boardAdd(boardName);
	}

	public int defultBoard(int i) {
		Board board = articleDao.getBoard(i);
		return board.boardId;
	}

	public Board getBoard(int inputedId) {
		return articleDao.getBoard(inputedId);
	}

	public List<Article> getArticlesInBoard(int boardId) {
		List<Article> articlesInBoard = new ArrayList<>();
		for (Article article : articleDao.getArticles()) {
			if (article.boardId == boardId) {
				articlesInBoard.add(article);
			}
		}

		return articlesInBoard;
	}

}
