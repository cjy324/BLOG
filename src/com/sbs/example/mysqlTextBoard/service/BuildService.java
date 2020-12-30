package com.sbs.example.mysqlTextBoard.service;

import java.util.Collections;
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
		
		// site_template에 있는 images(원본)폴더를 복사해 site폴더 생성시 그 안에 복사본 붙여넣기
		Util.copyDir("site_template/images", "site/images");
		
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

			Collections.reverse(articles); //최신순 리스팅
			
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

	// 게시판 페이지별 게시물 리스트 생성
	private void buildArticleListPage(Board board, List<Article> articles, int articlesInAPage, int pageMenuBoxSize,
			int totalPages, int page) {

		System.out.println("= " + board.name + " 리스트 페이지 생성 =");

		String head = getHeadHtml("article_list_" + board.code);
		String sideBar = getSideBarHtml();
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
		html.append(sideBar);
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
		String sideBar = getSideBarHtml();
		String mainHtml = Util.getFileContents("site_template/index.html");

		html.append(head);
		html.append(mainHtml);
		html.append(sideBar);
		html.append(foot);

		String fileName = "index.html";
		String path = "site/" + fileName;

		Util.writeFile(path, html.toString());
	}

	// side-bar.html 가져오기
	private String getSideBarHtml() {
		List<Board> boards = articleService.getBoards();

		String sideBar = Util.getFileContents("site_template/side-bar.html");
		StringBuilder boardMenuLinkHtml = new StringBuilder();

		for (Board board : boards) {
			
			boardMenuLinkHtml.append("<li>");

			String link = board.code + "-list-1.html";

			boardMenuLinkHtml.append("<a href=\"" + link + "\" class=\"block\">");
			boardMenuLinkHtml.append(getTitleBarContentByPageName("article_list_" + board.code));
			boardMenuLinkHtml.append("</a>");
			boardMenuLinkHtml.append("</li>");

		}

		sideBar = sideBar.replace("[게시판 메뉴 링크 블록]", boardMenuLinkHtml.toString());

		return sideBar;
	}

	// 게시판 별 게시물 상세페이지 생성
	private void buildArticleDetailPages() {
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			List<Article> articles = articleService.getBoardArticlesByCodeForPrint(board.code);
			int articlesSize = articles.size();
			
			if (articlesSize <= 0) {
				continue;
			}
			int beforeArticleIndex = 0;
			int x = beforeArticleIndex;
			int beforeArticleId = articles.get(x).id;

		//	String head = getHeadHtml("article_detail");
			String sideBar = getSideBarHtml();
			String topButton = Util.getFileContents("site_template/top-button.html");
			String foot = Util.getFileContents("site_template/foot.html");

			String template = Util.getFileContents("site_template/detail.html");

			System.out.println("= article 상세페이지 생성 =");

			for (Article article : articles) {
				
				String head = getHeadHtml("article_detail", article);
				
				StringBuilder html = new StringBuilder();

				html.append(head);

				StringBuilder body = new StringBuilder();
				
				body.append("<div class=\"article-detail-cell__board-name\">");
				body.append("<div>");
				body.append("<span>게시판 : </span><span>"+ board.name +" 게시판</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__id\">");
				body.append("<div>");
				body.append("<span>게시물 번호 : </span><span>" + article.id + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__member-id\">");
				body.append("<div>");
				body.append("<span>작성자 : </span><span>" + article.extra_memberName + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__reg-date\">");
				body.append("<div>");
				body.append("<span>작성일 : </span><span>" + article.regDate + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__update-date\">");
				body.append("<div>");
				body.append("<span>수정일 : </span><span>" + article.updateDate + "</span>");
				body.append("</div><br>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__title\">");
				body.append("<div>");
				body.append("<span>제목 : </span><span>" + article.title + "</span>");
				body.append("</div>");
				body.append("</div>");
				body.append("<div class=\"article-detail-cell__body height-70p \">");
				body.append("<div>");
				body.append("<span>" + article.body + "</span>");
				body.append("</div>");
				body.append("</div><br><br>");
				/*
				 * //댓글 유틸 disqus 적용 body.append("<nav><div id=\"disqus_thread\"></div>\r\n" +
				 * "<script>\r\n" + "    (function() { // DON'T EDIT BELOW THIS LINE\r\n" +
				 * "    var d = document, s = d.createElement('script');\r\n" +
				 * "    s.src = 'https://devj-blog.disqus.com/embed.js';\r\n" +
				 * "    s.setAttribute('data-timestamp', +new Date());\r\n" +
				 * "    (d.head || d.body).appendChild(s);\r\n" + "    })();\r\n" +
				 * "</script>\r\n" +
				 * "<noscript>Please enable JavaScript to view the <a href=\"https://disqus.com/?ref_noscript\">comments powered by Disqus.</a></noscript></nav>"
				 * );
				 */
				
				
				// 상세페이지 하단 메뉴
				
				StringBuilder pageMenuBody = new StringBuilder();
				
				if (article.id > beforeArticleId) {
					pageMenuBody.append("<div class=\"./\"><a href=\"" + articles.get(x - 1).id + ".html"
							+ "\" class=\"hover-underline\">&lt 이전글</a></div>");
				}

				pageMenuBody.append("<div class=\"./\"><i class=\"fas fa-th-list\"></i><a href=\"" + board.code + "-list-1.html"
						+ "\" class=\"hover-underline\"> 목록 </a></div>");
				if (x < articlesSize - 1) {
					pageMenuBody.append("<div class=\"./\"><a href=\"" + articles.get(x + 1).id + ".html"
							+ "\"class=\"hover-underline\">다음글 &gt</a></div>");
				}

				String bodyTemplate = template.replace("[상세페이지 블록]", body); // list 템플릿에 mainBody 끼워넣고
				html.append(bodyTemplate.replace("[상세페이지 하단 메뉴 블록]", pageMenuBody)); // bodyTemplate에 다시 pageMenuBody 끼워넣기
				html.append(sideBar);
				html.append(topButton);
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
	
	// head.html 가져오기 오버라이드
	private String getHeadHtml(String pageName) {   // 만약, getHeadHtml()에 pageName만 있으면 getHeadHtml(pageName, null)로 리턴
		return getHeadHtml(pageName, null);
	}
	
	// head.html 가져오기
	private String getHeadHtml(String pageName, Object object) {
		List<Board> boards = articleService.getBoards();

		String head = Util.getFileContents("site_template/head.html"); // head.html 가져오기
		StringBuilder boardMenuContentHtml = new StringBuilder();
		
		for (Board board : boards) {
			boardMenuContentHtml.append("<li>");

			String link = board.code + "-list-1.html";

			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");
			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.code) + " BOARD");
			boardMenuContentHtml.append("</a>");
			boardMenuContentHtml.append("</li>");

		}

		head = head.replace("[게시판 이름 블록]", boardMenuContentHtml.toString());

		String titleBarContentHtml = getTitleBarContentByPageName(pageName);
		// 입력받은 pageName에 맞는 타이틀바 컨텐츠를 리턴

		head = head.replace("[타이틀바 컨텐츠]", titleBarContentHtml);
		
		String pageTitle = getPageTitle(pageName, object);
		// 입력받은 pageName에 맞는 페이지의 타이틀을 리턴
		
		head = head.replace("[페이지 타이틀]", pageTitle);
	
		//Meta Tag & Open Graph 작업
		String siteTitle = "Dev_J BLOG";
		String siteSubject = "Dev_J의 BLOG";
		String siteKeywords = "JAVA, HTML, CSS, JavaScript, MySQL";
		String siteDescription = "Dev_J의 BLOG입니다.";
		String siteDomain = "blog.devj.me"; 
		String siteMainUrl = "https://" + siteDomain;
		String currentDate = Util.getNowDateStr().replace(" ", "T");
		
		
		//게시물 상세페이지마다 내용 나오게 하기
		if ( object instanceof Article ) {
			Article article = (Article) object;
			siteSubject = article.title;
			siteDescription = article.body;
			siteDescription = siteDescription.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
			//[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s] => 모든 특수문자 제거
		}
		
		
		head = head.replace("[사이트 타이틀]", siteTitle);
		head = head.replace("[사이트 주제]", siteSubject);
		head = head.replace("[사이트 키워드]", siteKeywords);
		head = head.replace("[사이트 설명]", siteDescription);
		head = head.replace("[사이트 도메인]", siteDomain);
		head = head.replace("[사이트 URL]", siteMainUrl);
		head = head.replace("[날짜]", currentDate);
		
		
		return head;
	}

	// 입력받은 pageName에 맞는 페이지의 타이틀을 리턴
	private String getPageTitle(String pageName, Object object) {
		StringBuilder pageTitle = new StringBuilder();
		
		String forPrintPageName = pageName;
		
		if(forPrintPageName.equals("index")) {
			forPrintPageName = "home";
		}
		
		forPrintPageName = forPrintPageName.toUpperCase(); //대상 문자열을 모두 대문자로 변환
		forPrintPageName = forPrintPageName.replace("_", " "); //pageName에 있는 _를 공백으로 변환
		
		pageTitle.append("Dev_J BLOG | ");
		pageTitle.append(forPrintPageName);
		
		//object가 Article이라는 객체로 형변환이 가능한지를 판단
		if(object instanceof Article) {
			Article article = (Article) object;
			
			pageTitle.insert(0, article.title + " | ");
			//형변환이 가능하면 0번 위치에? article.title + "|" 삽입
		}
		
		return pageTitle.toString();
	}

	// 페이지이름에 따라 메인부분 타이틀바 아이콘 가져오기
	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-invoice\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-exclamation\"></i> <span>NOTICE</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fas fa-users\"></i> <span>FREE</span>";
		} else if (pageName.startsWith("article_list_java")) {
			return "<i class=\"fab fa-java\"></i> <span>JAVA</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"fas fa-clipboard-list\"></i> <span>NONAME BOARD</span>";
		}
		return "";
	}

}