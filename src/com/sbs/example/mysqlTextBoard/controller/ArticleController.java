package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.dto.View;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController extends Controller {

	Scanner sc;
	ArticleService articleService;

	public ArticleController() {
		sc = Container.scanner;
		articleService = Container.articleService;

	}

	public void doCmd(String cmd) {
		// 게시판 생성
		if (cmd.equals("article boardAdd")) {
			boardAdd(cmd);
		}
		// 게시판 선택
		else if (cmd.equals("article selectBoard")) {
			selcetBoard(cmd);
		}
		// 게시물 생성
		else if (cmd.equals("article add")) {
			add(cmd);
		}
		// 게시물 리스팅
		else if (cmd.equals("article list")) {
			list(cmd);
		}
		// 게시물 상세보기
		else if (cmd.startsWith("article detail ")) {
			detail(cmd);
		}
		// 게시물 수정
		else if (cmd.startsWith("article modify ")) {
			modify(cmd);
		}
		// 게시물 삭제
		else if (cmd.startsWith("article delete ")) {
			delete(cmd);
		}
		// 게시물 삭제
		else if (cmd.startsWith("article delete ")) {
			delete(cmd);
		}
		// 댓글 추가
		else if (cmd.startsWith("article reply ")) {
			reply(cmd);
		}
		// 댓글 수정
		else if (cmd.startsWith("article replyModify ")) {
			replyModify(cmd);
		}
		// 댓글 삭제
		else if (cmd.startsWith("article replyDelete ")) {
			replyDelete(cmd);
		}
		// 추천 추가
		else if (cmd.startsWith("article recommand ")) {
			recommand(cmd);
		}
		// 추천 취소
		else if (cmd.startsWith("article recommandCancel ")) {
			recommandCancel(cmd);
		}

	}

	// 추천 취소
	private void recommandCancel(String cmd) {
		System.out.println("== 게시물 추천 취소 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.println("(해당 게시물은 존재하지 않습니다.)");
			return;
		}

		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 후 이용가능");
			return;
		}

		int recommandMemberId = Container.session.loginMemberId;

		Recommand recommand = articleService.getRecommand(article.id, recommandMemberId);

		if (recommand == null) {
			System.out.println("(회원님은 해당 게시물을 추천하지 않았습니다.)");
			return;
		}

		articleService.recomandDelete(article.id, recommandMemberId);

		System.out.printf("(%d번 게시물 추천 취소)\n", inputedId);
		System.out.println("--------------------------------------------------");
	}

	// 추천 추가
	private void recommand(String cmd) {
		System.out.println("== 게시물 추천 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.println("(해당 게시물은 존재하지 않습니다.)");
			return;
		}

		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}

		int recommandMemberId = Container.session.loginMemberId;

		Recommand recommand = articleService.getRecommand(article.id, recommandMemberId);

		if (recommand != null) {
			System.out.println("(이미 추천하셨습니다.)");
			return;
		}

		int id = articleService.recommandAdd(article.id, recommandMemberId);

		System.out.printf("(%d번 게시물 추천 완료)\n", article.id);
		System.out.println("--------------------------------------------------");
	}

	// 댓글 삭제
	private void replyDelete(String cmd) {
		System.out.println("== 댓글 삭제 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Reply reply = articleService.getReply(inputedId);

		if (reply == null) {
			System.out.println("(해당 댓글은 존재하지 않습니다.)");
			return;
		}
		
		
		if (reply.replyMemberId != Container.session.loginMemberId) {
			System.out.println("(권한이 없습니다.)");
			return;
		}

		articleService.replyDelete(reply.id);

		System.out.printf("(%d번 댓글 삭제 완료)\n", inputedId);
		System.out.println("--------------------------------------------------");
	}

	// 댓글 수정
	private void replyModify(String cmd) {
		System.out.println("== 댓글 수정 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Reply reply = articleService.getReply(inputedId);

		if (reply == null) {
			System.out.println("(해당 댓글은 존재하지 않습니다.)");
			return;
		}
		if (reply.replyMemberId != Container.session.loginMemberId) {
			System.out.println("(권한이 없습니다.)");
			return;
		}

		System.out.printf("수정할 내용 입력) ");
		String replyBody = sc.nextLine();

		articleService.replyModify(reply.id, replyBody);

		System.out.printf("(%d번 댓글 수정 완료)\n", reply.id);
		System.out.println("--------------------------------------------------");
	}

	// 댓글 추가
	private void reply(String cmd) {
		System.out.println("== 댓글 입력 ==");

		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}
		
		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.println("(해당 게시물은 존재하지 않습니다.)");
			return;
		}

		System.out.printf("댓글 내용 입력) ");
		String replyBody = sc.nextLine();
		int replyMemberId = Container.session.loginMemberId;

		int id = articleService.replyAdd(article.id, replyBody, replyMemberId);

		System.out.printf("(%d번 게시물, %d번 댓글 생성 완료)\n", article.id, id);
		System.out.println("--------------------------------------------------");
	}

	// 게시물 삭제
	private void delete(String cmd) {
		System.out.println("== 게시물 수정 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.println("(해당 게시물은 존재하지 않습니다.)");
			return;
		}
		if (article.memberId != Container.session.loginMemberId) {
			System.out.println("(권한이 없습니다.)");
			return;
		}

		articleService.articleDelete(article.id);

		System.out.printf("(%d번 게시물 삭제 완료)\n", inputedId);
		System.out.println("--------------------------------------------------");
	}

	// 게시물 수정
	private void modify(String cmd) {
		System.out.println("== 게시물 수정 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.println("(해당 게시물은 존재하지 않습니다.)");
			return;
		}
		if (article.memberId != Container.session.loginMemberId) {
			System.out.println("(권한이 없습니다.)");
			return;
		}

		System.out.printf("수정할 제목 입력) ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 입력) ");
		String body = sc.nextLine();

		articleService.articleModify(article.id, title, body);

		System.out.printf("(%d번 게시물 수정 완료)\n", article.id);
		System.out.println("--------------------------------------------------");
	}

	// 게시물 상세보기
	private void detail(String cmd) {

		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}
		
		System.out.println("== 게시물 상세보기 ==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.println("(해당 게시물은 존재하지 않습니다.)");
			return;
		}
		Board board = articleService.getBoardById(article.boardId);
		List<Recommand> recommands = articleService.getRecommands(article.id);

		articleService.addView(article.id);
		List<View> views = articleService.getViews(article.id);

		System.out.println("== 게시물 정보 ==");
		System.out.printf("번호: %d\n", article.id);
		System.out.printf("작성일: %s\n", article.regDate);
		System.out.printf("최근수정일: %s\n", article.updateDate);
		System.out.printf("제목: %s\n", article.title);
		System.out.printf("내용: %s\n", article.body);
		System.out.printf("추천수: %d\n", recommands.size());
		System.out.printf("조회수: %d\n", views.size());
		System.out.printf("작성자: %s\n", article.extra_memberName);
		System.out.printf("게시판 이름: %s\n", board.name);
		System.out.println("-------- 댓글 --------");

		List<Reply> replies = articleService.getRepliesForPrint(article.id);

		if (replies.size() <= 0) {
			System.out.println("(댓글이 없습니다.)");
			System.out.println("--------------------------------------------------");
			return;
		}

		for (Reply reply : replies) {
			System.out.printf("%d번 댓글 - %s / 작성자 - %s / 작성일 - %s\n", reply.id, reply.replyBody, reply.extra_memberName,
					reply.regDate);
		}
		System.out.println("----------------------------------------------------------------------------------------------");

	}

	// 게시물 리스팅
	private void list(String cmd) {
		
		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}

		Board board = articleService.getBoardById(Container.session.selectedBoardId);

		System.out.printf("== %s 게시판 글 리스트 ==\n", board.name);

		List<Article> articles = articleService.getBoardArticlesForPrint(board.id);

		if (articles.size() <= 0) {
			System.out.println("(현재 등록된 게시물이 없습니다.)");
			return;
		}

		System.out.println("번호 / 제목 / 작성일 / 작성자 / 조회수 / 추천수");

		for (Article article : articles) {
			List<Recommand> recommands = articleService.getRecommands(article.id);
			List<View> views = articleService.getViews(article.id);
			System.out.printf("%d / %s / %s / %s / %d / %d \n", article.id, article.title, article.regDate,
					article.extra_memberName, recommands.size(), views.size());
		}
		System.out.println("------------------------------------------------------------------------");
	}

	// 게시물 생성
	private void add(String cmd) {
		System.out.println("== 게시물 생성 ==");

		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}

		System.out.printf("게시물 제목 입력) ");
		String title = sc.nextLine();
		System.out.printf("게시판 내용 입력) ");
		String body = sc.nextLine();
		int boardId = Container.session.selectedBoardId;
		int memberId = Container.session.loginMemberId;

		int id = articleService.add(boardId, title, body, memberId);

		System.out.printf("(%d번 게시물 생성 완료)\n", id);
		System.out.println("--------------------------------------------------");

	}

	// 게시판 선택
	private void selcetBoard(String cmd) {
		
		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}

		List<Board> boards = articleService.getBoards();
		
		System.out.println("--------- 게시판 현황 ---------");
		for(Board board : boards) {
			List<Article> articles = articleService.getBoardArticlesForPrint(board.id);
			System.out.printf("번호: %s / 이름: %s / 코드: %s / 게시물 수: %d\n", board.id, board.name, board.code, articles.size());
		}
		
		System.out.println("-------------------------------------------------------------");
		System.out.println("== 게시판 선택 ==");
		System.out.printf("선택할 게시판 코드 입력) ");
		String code = sc.nextLine();
		
		Board board = articleService.getBoardByCode(code);

		if (board == null) {
			System.out.println("(해당 게시판이 없습니다. 코드를 확인하세요.)");
			return;
		}

		System.out.printf("((%s) 게시판 선택 완료)\n", board.name);
		System.out.println("--------------------------------------------------");
		Container.session.selectedBoardId = board.id;

	}

	// 게시판 생성
	private void boardAdd(String cmd) {
		System.out.println("== 게시판 생성 ==");

		if (Container.session.loginAdminMemberStatus() == false) {
			System.out.println("('관리자'만 생성이 가능합니다.)");
			return;
		}

		System.out.printf("게시판 이름 입력) ");
		String name = sc.nextLine();
		System.out.printf("게시판 코드 입력) ");
		String code = sc.nextLine();

		int id = articleService.boardAdd(name, code);

		System.out.printf("(%d번 게시판 생성 완료)\n", id);
		System.out.println("--------------------------------------------------");
	}

}
