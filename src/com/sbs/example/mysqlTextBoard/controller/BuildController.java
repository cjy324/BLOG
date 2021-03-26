package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.service.BuildService;

public class BuildController extends Controller {
	Scanner sc;
	BuildService buildService;

	public BuildController() {
		sc = Container.scanner;
		buildService = Container.buildService;

	}

	public void doCmd(String cmd) {
		// 사이트 생성 명령어 필터링
		if (cmd.equals("build site")) {
			buildSite(cmd);
		}

	}

	// 사이트 생성
	private void buildSite(String cmd) {
		System.out.println("== 사이트 생성 ==");
		buildService.builSite();
	}

}
