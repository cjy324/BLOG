package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController extends Controller{

	Scanner sc;
	MemberService memberService;

	public MemberController() {
		sc = Container.scanner;
		memberService = Container.memberService;
	}

	public void doCmd(String cmd) {
		// 회원가입
		if (cmd.equals("member join")) {
			join(cmd);
		}
		// 로그인
		else if (cmd.equals("member login")) {
			login(cmd);
		}
		// whoami
		else if (cmd.equals("member whoami")) {
			whoami(cmd);
		}
		// 로그아웃
		else if (cmd.equals("member logout")) {
			logout(cmd);
		}
	}

	// 로그아웃
	private void logout(String cmd) {
		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}
		
		Container.session.loginMemberId = 0;
		Container.session.loginAdminMemberId = 0;
		
		System.out.println("== 로그 아웃 ==");
		System.out.println("--------------------------------------------------");
	}

	// whoami
	private void whoami(String cmd) {
		if (Container.session.loginStatus() == false) {
			System.out.println("(로그인 후 이용가능)");
			return;
		}

		Member member = memberService.getMemberById(Container.session.loginMemberId);

		System.out.println("== 회원 정보 ==");
		System.out.printf("회원 번호: %d\n", member.getId());
		System.out.printf("회원 아이디: %s\n", member.getLoginId());
		System.out.printf("회원 이름: %s\n", member.getName());
		System.out.printf("회원 유형: %s\n", member.memberType());
		System.out.println("--------------------------------------------------");

	}

	// 로그인
	private void login(String cmd) {

		System.out.println("== 로그인 ==");

		if (Container.session.loginStatus() == true) {
			System.out.println("(로그아웃 후 이용가능)");
			return;
		}

		System.out.printf("아이디) ");
		String loginId = sc.nextLine();

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("(해당 아이디가 존재하지 않습니다.)");
			return;
		}

		System.out.printf("비밀번호 입력) ");
		String loginPw = sc.nextLine();

		if (member.getLoginPw().equals(loginPw) == false) {
			System.out.println("(비밀번호가 틀렸습니다.)");
			return;
		}

		if (member.isAdmin()) {
			System.out.printf("('관리자', 로그인 완료)\n");
			Container.session.loginMemberId = member.getId();
			Container.session.loginAdminMemberId = member.getId();
			System.out.println("--------------------------------------------------");
			return;
		}
		System.out.printf("(%s님, 로그인 완료)\n", member.getName());
		Container.session.loginMemberId = member.getId();
		System.out.println("--------------------------------------------------");
	}

	// 회원가입
	private void join(String cmd) {

		System.out.println("== 회원가입 ==");

		if (Container.session.loginStatus() == true) {
			System.out.println("(로그아웃 후 이용가능)");
			return;
		}

		System.out.printf("아이디 입력) ");
		String loginId = sc.nextLine();

		Member member = memberService.getMemberByLoginId(loginId);

		if (member != null) {
			System.out.println("(해당 아이디 이미 사용중)");
			return;
		}

		System.out.printf("비밀번호 입력) ");
		String loginPw = sc.nextLine();

		System.out.printf("이름 입력) ");
		String name = sc.nextLine();

		int id = memberService.join(loginId, loginPw, name);

		System.out.printf("(%d번 회원 회원가입 완료)\n", id);
		System.out.println("--------------------------------------------------");
	}

}