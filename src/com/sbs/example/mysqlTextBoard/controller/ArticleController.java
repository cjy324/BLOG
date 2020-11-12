package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController {

	Scanner sc;
	ArticleService articleService;

	public ArticleController() {
		sc = Container.scanner;
		articleService = new ArticleService();
	}

	public void list(String cmd) {

		System.out.println("== 게시물 리스트 ==");
		System.out.println("번호 / 제목 / 작성일 / 수정일 / 작성자");

		List<Article> articles = articleService.getArticles();

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %s / %d\n", article.id, article.title, article.regDate, article.updateDate,
					article.memberId);
		}

	}

	public void add(String cmd) {
		
		System.out.printf("제목 입력) ");
		String title = sc.nextLine();
		System.out.printf("내용 입력) ");
		String body = sc.nextLine();
		int memberId = 1;
		int boardId = 1;

		int id = articleService.add(title,body,memberId,boardId);
		
		System.out.printf("게시물 생성 완료\n");
	}

}
