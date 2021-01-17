package com.sbs.example.mysqlTextBoard.container;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.AppConfig;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BuildController;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dao.Ga4DataDao;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dao.TagDao;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.BuildService;
import com.sbs.example.mysqlTextBoard.service.DiscusApiService;
import com.sbs.example.mysqlTextBoard.service.GoogleAnalyticsApiService;
import com.sbs.example.mysqlTextBoard.service.MemberService;
import com.sbs.example.mysqlTextBoard.service.TagService;
import com.sbs.example.mysqlTextBoard.session.Session;

public class Container {

	public static Scanner scanner;
	public static Session session;
	public static AppConfig appConfig;
	public static ArticleController articleController;
	public static ArticleService articleService;
	public static MemberController memberController;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static MemberDao memberDao;
	public static BuildController buildController;
	public static BuildService buildService;
	public static DiscusApiService discusApiService;
	public static GoogleAnalyticsApiService googleAnalyticsApiService;
	public static Ga4DataDao ga4DataDao;
	public static TagService tagService;
	public static TagDao tagDao;
	
	static {
		scanner = new Scanner(System.in);
		session = new Session();
		appConfig = new AppConfig();
		
		tagDao = new TagDao();
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		ga4DataDao = new Ga4DataDao();
		
		
		tagService = new TagService();
		memberService = new MemberService();
		articleService = new ArticleService();
		discusApiService = new DiscusApiService();
		googleAnalyticsApiService = new GoogleAnalyticsApiService();
		buildService = new BuildService();
		
		memberController = new MemberController();
		articleController = new ArticleController();
		buildController = new BuildController();
	}
}
