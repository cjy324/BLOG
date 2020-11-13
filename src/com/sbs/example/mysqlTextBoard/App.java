package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class App {

	Scanner sc;
	ArticleController articleController;
	ArticleService articleService;
	MemberController memberController;

	public App() {
		sc = Container.scanner;
		articleController = Container.articleController;
		articleService = Container.articleService;
		memberController = Container.memberController;

		init();
	}

	private void init() {
		Container.session.selectedBoardId = articleService.defultBoard(1);

	}

	public void run() {

		while (true) {
			System.out.printf("명령어 입력) ");
			String cmd = sc.nextLine();

			if (cmd.equals("exit")) {
				System.out.println("종료");
				break;
			}

			Controller controller = getControllerByCmd(cmd);
				if (controller != null) {
					controller.doCmd(cmd);
			}

		}

		sc.close();
	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article ")) {
			return articleController;
		} else if (cmd.startsWith("member ")) {
			return memberController;
		}
		return null;
	}

}
