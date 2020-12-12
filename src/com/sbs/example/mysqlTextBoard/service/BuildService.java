package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {
	ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;

	}

	public void builSite() {
		System.out.println("= site/article 폴더 생성 =");
		Util.mkdir("site/article");

		List<Article> articles = articleService.getArticlesForPrint();

		int articleIndex = 0;

		for (Article article : articles) {
			StringBuilder html = new StringBuilder();
			
			//게시물 1개당 1개의 html 작성
			html.append("<!doctype html>");
			html.append("<html lang=\"en\">");

			html.append("<head>");
			html.append("<meta charset=\"UTF-8\" />");
			html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
			html.append("<title>게시물 상세페이지 - " + article.title + "</title>");
			html.append("</head>");

			
			html.append("<body>");
			html.append("<h1>게시물 상세페이지</h1>");
			
			html.append("<div>");
			html.append("번호 : " + article.id + "<br>");
			html.append("작성일 : " + article.regDate + "<br>");
			html.append("수정일 : " + article.updateDate + "<br>");
			html.append("제목 : " + article.title + "<br>");
			html.append("작성자 : " + article.extra_memberName + "<br>");
			html.append("내용 : " + article.body + "<br>");
			if(articleIndex > 0) {
				html.append("<a href=\""+ (article.id-1) + ".html" +"\">이전글</a><br>");
			}
			if(articleIndex < articles.size()-1) {
				html.append("<a href=\""+ (article.id+1) + ".html" +"\">다음글</a><br>");
			}
			
			
			html.append("</div>");
			
			html.append("</body>");

			html.append("</html>");

			String fileName = article.id + ".html";
			String path = "site/article/" + fileName;
			
			Util.writeFile(path, html.toString());
			
			System.out.println(path + " 생성");
			articleIndex++;  
		}
	}

}