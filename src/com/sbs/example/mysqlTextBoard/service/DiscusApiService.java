package com.sbs.example.mysqlTextBoard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.apiDto.DisqusApiDataListThread;
import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.util.Util;

public class DiscusApiService {

	public Map<String, Object> getArticleData(Article article) {
		String fileName = Container.buildService.getArticleFileName(article);
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";

		DisqusApiDataListThread disqusApiDataListThread = (DisqusApiDataListThread) Util.callApiResponseTo(DisqusApiDataListThread.class,
				url, "api_key=" + Container.appConfig.getDisqusApiKey(), "forum=" + Container.appConfig.getDisqusForumName(),
				"thread:ident=" + fileName);
		
		if(disqusApiDataListThread == null) {
			return null;
		}
		
		Map<String, Object> rs = new HashMap<>();
		rs.put("likesCount", disqusApiDataListThread.response.get(0).likes);
		rs.put("commentsCount", disqusApiDataListThread.response.get(0).posts);
		System.out.println(rs);
		
		return rs;
	}

}
