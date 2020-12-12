package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BuildController;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;

public class App {

	Scanner sc;
	ArticleController articleController;
	MemberController memberController;
	BuildController buildController;

	public App() {
		sc = Container.scanner;
		articleController = Container.articleController;
		memberController = Container.memberController;
		buildController = Container.buildController;

	}

	public void run() {

		while (true) {
			System.out.printf("명령어 입력) ");
			String cmd = sc.nextLine();

			MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");

			boolean needToExit = false;

			if (cmd.equals("exit")) {
				System.out.println("종료");
				needToExit = true;
			}

			Controller controller = getControllerByCmd(cmd);
			
			
			if (controller != null) {
				controller.doCmd(cmd);
			}

			if (needToExit == true) {
				MysqlUtil.closeConnection();
				break;
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
		else if (cmd.startsWith("build ")) {
			return buildController;
		}
		return null;
	}

}
