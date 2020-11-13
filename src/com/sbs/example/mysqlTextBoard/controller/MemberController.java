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
		// logout
		else if (cmd.equals("member logout")) {
			logout(cmd);
		}
		// whoami
		else if (cmd.equals("member whoami")) {
			whoami(cmd);
		}

	}

	private void whoami(String cmd) {
		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}

		int loginedMemberId = Container.session.loginedMemberId;

		Member member = memberService.getEqualsMemberByMemberId(loginedMemberId);

		System.out.println("== 회원 정보 ==");

		System.out.printf("회원번호 : %d\n", member.memberId);
		System.out.printf("회원ID : %s\n", member.loginId);
		System.out.printf("회원PW : %s\n", member.loginPw);
		System.out.printf("회원이름 : %s\n", member.name);

	}

	private void logout(String cmd) {
		if (Container.session.loginStatus() == false) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}

		Container.session.loginedMemberId = 0;

		System.out.println("로그아웃 되었습니다.");

	}

	private void login(String cmd) {

		if (Container.session.loginStatus() == true) {
			System.out.println("로그아웃 먼저 해주세요.");
			return;
		}

		System.out.printf("아이디 입력 : ");
		String loginId = sc.nextLine();

		Member member = memberService.getEqualsLogindId(loginId);

		if (member == null) {
			System.out.println("해당 아이디가 없습니다.");
			return;
		}

		System.out.printf("비밀번호 입력 : ");
		String loginPw = sc.nextLine();

		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호가 틀렸습니다.");
			return;
		}

		System.out.printf("'%s'님 로그인 완료\n", member.name);
		Container.session.loginedMemberId = member.memberId;

	}

	private void join(String cmd) {

		if (Container.session.loginStatus() == true) {
			System.out.println("로그아웃 먼저 해주세요.");
			return;
		}

		System.out.printf("아이디 입력 : ");
		String createId = sc.nextLine();

		boolean checkEqualsLogindId = memberService.checkEqualsLogindId(createId);

		if (checkEqualsLogindId == true) {
			System.out.println("해당 아이디는 이미 사용중입니다.");
			return;
		}
		System.out.printf("비밀번호 입력 : ");
		String createPw = sc.nextLine();
		System.out.printf("이름 입력 : ");
		String name = sc.nextLine();

		int memberId = memberService.join(createId, createPw, name);

		System.out.printf("'%s' 회원 회원가입 완료\n", createId);
	}

}
