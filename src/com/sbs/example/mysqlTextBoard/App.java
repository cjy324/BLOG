package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;

public class App {

	Scanner sc;
	ArticleController articleController;

	public App() {
		sc = Container.scanner;
		articleController = new ArticleController();
	}

	public void run() {

		while(true) {
			System.out.printf("명령어 입력) ");
			String cmd = sc.nextLine();
			
			if(cmd.equals("article add")) {
				articleController.add(cmd);
			}
			else if(cmd.equals("article list")) {
				articleController.list(cmd);
			}
			else if(cmd.startsWith("article delete ")) {
				articleController.delete(cmd);
			}
			else if(cmd.startsWith("article modify ")) {
				articleController.modify(cmd);
			}
			else if(cmd.startsWith("article detail ")) {
				articleController.detail(cmd);
			}
			
			if(cmd.equals("exit")) {
				System.out.println("종료");
				break;	
			}
		}
		
		
		sc.close();
	}

}
