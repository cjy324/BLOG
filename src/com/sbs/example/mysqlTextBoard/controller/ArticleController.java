package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class ArticleController extends Controller{

	Scanner sc;
	ArticleService articleService;
	MemberService memberService;

	public ArticleController() {
		sc = Container.scanner;
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void doCmd(String cmd) {
		// 게시물 추가
		if (cmd.equals("article add")) {
			add(cmd);
		}
		// 게시물 리스팅
		else if (cmd.equals("article list")) {
			list(cmd);
		}
		// 게시물 삭제
		else if (cmd.startsWith("article delete ")) {
			delete(cmd);
		}
		// 게시물 수정
		else if (cmd.startsWith("article modify ")) {
			modify(cmd);
		}
		// 게시물 상세보기
		else if (cmd.startsWith("article detail ")) {
			detail(cmd);
		}
		// 게시판 추가
		else if (cmd.equals("article boardAdd")) {
			boardAdd(cmd);
		}
		// 게시판 선택
		else if (cmd.startsWith("article selectBoard ")) {
			selectBoard(cmd);
		}

	}

	private void selectBoard(String cmd) {
		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Board board = articleService.getBoard(inputedId);

		if (board == null) {
			System.out.println("해당 게시판이 없습니다.");
			return;
		}

		System.out.printf("%s 게시판 선택 완료\n", board.boardName);
		Container.session.selectedBoardId = board.boardId;

	}

	private void boardAdd(String cmd) {
		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 후 이용가능");
			return;
		}

		System.out.printf("게시판 이름 입력) ");
		String boardName = sc.nextLine();

		int boardId = articleService.boardAdd(boardName);

		System.out.printf("%s 게시판 생성 완료\n", boardName);

	}

	private void list(String cmd) {
		
		int selectedBoard = Container.session.selectedBoardId;
		
		Board board = articleService.getBoard(selectedBoard);
		
		List<Article> articles = articleService.getArticlesInBoard(board.boardId);
		
		if(articles.size() <= 0) {
			System.out.println("해당 게시판에 등록된 게시물이 없습니다.");
			return;
		}

		System.out.printf("== %s 게시판 게시물 리스트 ==\n",board.boardName);
		System.out.println("번호 / 제목 / 작성일 / 수정일 / 작성자");
		
		for (Article article : articles) {
			Member member = memberService.getEqualsMemberByMemberId(article.memberId);
			System.out.printf("%d / %s / %s / %s / %s\n", article.id, article.title, article.regDate,
					article.updateDate, member.name);
		}

	}

	private void add(String cmd) {

		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		
		System.out.printf("제목 입력) ");
		String title = sc.nextLine();
		System.out.printf("내용 입력) ");
		String body = sc.nextLine();
		int memberId = Container.session.loginedMemberId;
		int boardId = Container.session.selectedBoardId;

		int id = articleService.add(title, body, memberId, boardId);

		
		System.out.printf("%d번 게시물 생성 완료\n",id);
	}

	private void delete(String cmd) {

		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 후 이용가능");
			return;
		}

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		articleService.deleteArticleById(inputedId);

		System.out.printf("%d번 게시물 삭제 완료\n", inputedId);

	}

	private void modify(String cmd) {

		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 후 이용가능");
			return;
		}

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		System.out.println("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.println("수정할 내용 : ");
		String body = sc.nextLine();

		articleService.modifyArticle(inputedId, title, body);

		System.out.printf("%d번 게시물 수정 완료\n", inputedId);

	}

	private void detail(String cmd) {

		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 후 이용가능");
			return;
		}

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getDetailArticleById(inputedId);
		
		if(article == null) {
			System.out.println("해당 게시물은 존재하지 않습니다.");
			return;
		}

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성일자 : %s\n", article.regDate);
		System.out.printf("수정일자 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		System.out.printf("작성자 : %d\n", article.memberId);
		System.out.printf("게시판 번호 : %d\n", article.boardId);

	}

}
