package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {
	ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;

	}

	public void builSite() {
		System.out.println("= site 폴더 생성 =");

	//	Util.rmdir("site"); // 기존 site 폴더 삭제
		Util.mkdir("site"); // 신규 site 폴더 생성

		// site_template에 있는 app.css(원본)를 복사해 site폴더 생성시 그 안에 복사본 붙여넣기
		Util.copy("site_template/app.css", "site/app.css");
		Util.copy("site_template/app.js", "site/app.js");

		buildIndexPage(); // 인덱스 페이지 생성
		buildArticleListPages(); // 각 게시판 별 게시물리스트 페이지 생성
		buildArticleDetailPages(); // 게시판 별 게시물 상세페이지 생성

	}

	// 각 게시판 별 게시물리스트 페이지 생성
	private void buildArticleListPages() {

		System.out.println("= article 리스트 페이지 생성 =");

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {

			List<Article> articles = articleService.getBoardArticlesByCodeForPrint(board.code);

			int articlesInAPage = 10; // 한 페이지에 들어갈 article 수 설정
			int pageMenuBoxSize = 5; // 한 메인페이지 화면에 나올 하단 페이지 메뉴 버튼 수 ex) 1 2 3 4 5 6 7 8 9 10
			int totalArticlesCount = articles.size(); // 전체 article의 수 카운팅
			int totalPages = (int) Math.ceil((double) totalArticlesCount / articlesInAPage); // 총 필요 페이지 수 카운팅

			// 각각의 페이지를 한개씩 반복적으로 생성
			// 총 필요 페이지 수까지
			for (int page = 1; page <= totalPages; page++) {
				buildArticleListPage(board, articles, articlesInAPage, pageMenuBoxSize, totalPages, page);
			}
		}

		System.out.println("= article 리스트 페이지 생성 종료 =");
	}

	private void buildArticleListPage(Board board, List<Article> articles, int articlesInAPage, int pageMenuBoxSize,
			int totalPages, int page) {

		System.out.println("= " + board.name + " 리스트 페이지 생성 =");

		String head = getHeadHtml("article_list_" + board.code);
		String foot = Util.getFileContents("site_template/foot.html");

		StringBuilder html = new StringBuilder();

		String template = Util.getFileContents("site_template/list.html");

		html.append(head);

		// 1~10 11~20 21~30 ......
		int startPoint = (page - 1) * articlesInAPage; // page=1일때 index=0(즉,id = 1) 2 10(11) 3 20(21)
		int endPoint = startPoint + articlesInAPage - 1; // page=1일때 index0~9 -> id1~10

		if (endPoint >= articles.size() - 1) {
			endPoint = articles.size() - 1;
		}

		StringBuilder mainBody = new StringBuilder();
		for (int i = startPoint; i <= endPoint; i++) {
			Article article = articles.get(i);

			mainBody.append("<div>");
			mainBody.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
			mainBody.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
			mainBody.append("<div class=\"article-list__cell-writer\">" + article.extra_memberName + "</a></div>");
			mainBody.append("<div class=\"article-list__cell-title\"><a href=\"" + article.id
					+ ".html\" class=\"hover-underline\">" + article.title + "</a></div>");
			mainBody.append("</div>");
		}

		// 하단 페이지 이동 버튼 메뉴 만들기
		// 1. pageMenuBox내 시작 번호, 끝 번호 설정

		int previousPageNumCount = (page - 1) / pageMenuBoxSize; // 현재 페이지가 2이면 previousPageNumCount = 1/5
		int boxStartNum = pageMenuBoxSize * previousPageNumCount + 1; // 총 페이지 수 30이면 1~5 6~10 11~15
		int boxEndNum = pageMenuBoxSize + boxStartNum - 1;

		if (boxEndNum > totalPages) {
			boxEndNum = totalPages;
		}

		// 2. '이전','다음' 버튼 페이지 계산
		int boxStartNumBeforePage = boxStartNum - 1;
		if (boxStartNumBeforePage < 1) {
			boxStartNumBeforePage = 1;
		}
		int boxEndNumAfterPage = boxEndNum + 1;
		if (boxEndNumAfterPage > totalPages) {
			boxEndNumAfterPage = totalPages;
		}

		// 3. '이전','다음' 버튼 필요 유무 판별
		boolean boxStartNumBeforePageBtnNeedToShow = boxStartNumBeforePage != boxStartNum;
		boolean boxEndNumAfterPageBtnNeedToShow = boxEndNumAfterPage != boxEndNum;

		StringBuilder pageMenuBody = new StringBuilder();

		link(board, page);

		if (boxStartNumBeforePageBtnNeedToShow) {
			pageMenuBody.append("<li><a href=\"" + link(board, boxStartNumBeforePage)
					+ "\" class=\"flex flex-ai-c\"> &lt; 이전</a></li>");
		}

		for (int i = boxStartNum; i <= boxEndNum; i++) {
			String selectedPageNum = "";
			if (i == page) {
				selectedPageNum = "article-page-menu__link--selected";
			}
			pageMenuBody.append("<li><a href=\"" + link(board, i) + "\" class=\"flex flex-ai-c " + selectedPageNum
					+ "\">" + i + "</a></li>");
		}
		if (boxEndNumAfterPageBtnNeedToShow) {
			pageMenuBody.append("<li><a href=\"" + link(board, boxEndNumAfterPage)
					+ "\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");
		}

		String bodyTemplate = template.replace("[게시물 리스트 블록]", mainBody); // list 템플릿에 mainBody 끼워넣고
		html.append(bodyTemplate.replace("[게시물 리스트 페이지메뉴 블록]", pageMenuBody)); // bodyTemplate에 다시 pageMenuBody 끼워넣기
		html.append(foot);

		String fileName = board.code + "-list-" + page + ".html";

		Util.writeFile("site/" + fileName, html.toString());

		System.out.println("= " + board.name + " 리스트 " + page + "페이지 생성 완료 =");

	}

	private String link(Board board, int page) {
		return board.code + "-list-" + page + ".html";

	}

	// 인덱스 페이지 생성
	private void buildIndexPage() {
		StringBuilder html = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/foot.html");

		String mainHtml = Util.getFileContents("site_template/index.html");

		html.append(head);
		html.append(mainHtml);
		html.append(foot);

		String fileName = "index.html";
		String path = "site/" + fileName;

		Util.writeFile(path, html.toString());
	}

	// 게시판 별 게시물 상세페이지 생성
	private void buildArticleDetailPages() {
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			List<Article> articles = articleService.getBoardArticlesByCodeForPrint(board.code);
			int articlesSize = articles.size();
			if (articlesSize <= 0) {
				break;
			}
			int beforeArticleIndex = 0;
			int x = beforeArticleIndex;
			int beforeArticleId = articles.get(x).id;

			String head = getHeadHtml("article_detail");
			String foot = Util.getFileContents("site_template/foot.html");

			String template = Util.getFileContents("site_template/detail.html");

			System.out.println("= article 상세페이지 생성 =");

			for (Article article : articles) {

				StringBuilder html = new StringBuilder();

				html.append(head);

				StringBuilder body = new StringBuilder();

				body.append("<div class=\"article-detail-cell__id\">");
				body.append("<div>");
				body.append("<span>번호 : </span><span>" + article.id + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__title\">");
				body.append("<div>");
				body.append("<span>제목 : </span><span>" + article.title + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__title\">");
				body.append("<div>");
				body.append("<span>작성자 : </span><span>" + article.extra_memberName + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__title\">");
				body.append("<div>");
				body.append("<span>작성일 : </span><span>" + article.regDate + "</span>");
				body.append("</div>");
				body.append("</div><br>");
				body.append("<div class=\"article-detail-cell__body height-70p \">");
				body.append("<div>");
				body.append("<span>" + article.body + "</span>");
				body.append("</div>");
				body.append("</div><br><br>");

				body.append(
						"</div></section><section class=\"section-3 con-min-width\"><div class=\"con\"><div class=\"article-list-bottom-cell flex flex-jc-c\">");

				if (article.id > beforeArticleId) {
					body.append("<div class=\"./\"><a href=\"" + articles.get(x - 1).id + ".html"
							+ "\" class=\"hover-underline\">&lt 이전글</a></div>");
				}

				body.append("<div class=\"./\"><i class=\"fas fa-th-list\"></i><a href=\"" + board.code + "-list-1.html"
						+ "\" class=\"hover-underline\"> 목록 </a></div>");
				if (x < articlesSize - 1) {
					body.append("<div class=\"./\"><a href=\"" + articles.get(x + 1).id + ".html"
							+ "\"class=\"hover-underline\">다음글 &gt</a></div>");
				}

				html.append(template.replace("[상세페이지 블록]", body));
				html.append(foot);

				String fileName = article.id + ".html";
				String path = "site/" + fileName;

				Util.writeFile(path, html.toString());

				System.out.println(path + " 생성");
				x++;
				beforeArticleId = articles.get(x - 1).id;

			}
			System.out.println("= article 상세페이지 생성 종료 =");

		}
	}

	// head.html 가져오기
	private String getHeadHtml(String pageName) {
		List<Board> boards = articleService.getBoards();

		String head = Util.getFileContents("site_template/head.html"); // head.html 가져오기
		StringBuilder boardMenuContentHtml = new StringBuilder();

		for (Board board : boards) {
			boardMenuContentHtml.append("<li>");

			String link = board.code + "-list-1.html";

			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");

			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.code));

			boardMenuContentHtml.append("</a>");

			boardMenuContentHtml.append("</li>");

		}

		head = head.replace("[게시판 이름 블록]", boardMenuContentHtml.toString());

		String titleBarContentHtml = getTitleBarContentByPageName(pageName);
		// 입력받은 pageName에 맞는 타이틀바 컨텐츠를 리턴

		head = head.replace("[타이틀바 컨텐츠]", titleBarContentHtml);

		return head;
	}

	// 페이지이름에 따라 메인부분 타이틀바 아이콘 가져오기
	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-invoice\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-exclamation\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fas fa-users\"></i> <span>FREE LIST</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"fas fa-clipboard-list\"></i> <span>ARTICLE LIST</span>";
		}
		return "";
	}

}