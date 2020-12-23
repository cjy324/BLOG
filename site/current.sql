/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.4.6-MariaDB : Database - textBoard
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`textBoard` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `textBoard`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `title` char(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `body` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `boardId` int(10) NOT NULL,
  `memberId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article` */

insert  into `article`(`id`,`regDate`,`updateDate`,`title`,`body`,`boardId`,`memberId`) values 
(1,'2020-12-19 17:19:16','2020-12-19 17:19:16','Dev_J Blog에 오신것을 환영합니다.','이 블로그는 개인 블로그이며, \r\n<br>\r\n주로 프로그래밍, IT 관련 내용을 다룰 예정입니다.',1,1),
(2,'2020-12-17 18:07:18','2020-12-19 17:19:22','1강. STS 개발툴 간단 소개','# < Spring Tool Suite >\r\n: 스프링 툴 스위트(STS, Spring Tool Suite)란 이클립스 기반 스프링 애플리케이션을 개발 하기 위한 개발 환경(툴)이다.\r\n\r\n### Project\r\n\r\n- 프로그램을 만들기 위한 파일들을 모아놓은 폴더\r\n\r\n### src\r\n\r\n- 패키지들을 모아놓는 폴더\r\n\r\n### package\r\n\r\n- 각각의 기능을 수행하는 클래스들을 모아놓은 폴더\r\n\r\n### class\r\n\r\n- 각각의 기능을 수행하는 소스코드 묶음\r\n\r\n\r\n\r\n# < 기본 단축어 정리 >\r\n\r\n* 새 프로젝트 만들기 단축키: alt+shift+N\r\n* **자동 완성 기능: ctrl+space**\r\n* **저장: ctrl+S**\r\n* **자동 줄맞춤: ctrl+shift+F**\r\n* **블록 주석:  ctrl+shift+/**\r\n* **주석 풀기: ctrl+shift+\\\\**\r\n* **코드 실행: ctrl+F11**\r\n* **import java util: ctrl+shift+O**\r\n* 문장 맨 앞으로: Home\r\n* 문장 맨 뒤로: End\r\n* 빨간줄 발생시 커서 위치시키고 ctrl+1\r\n* **동일 변수명 한번에 바꾸기 단축키: alt+shift+r**\r\n* Home : 줄에서 맨 앞으로 이동\r\n* End : 줄에서 맨 뒤으로 이동\r\n* Ctrl + Home : 맨 위으로 이동\r\n* Ctrl + End : 맨 아래으로 이동\r\n* Ctrl + Z : 이전 히스토리로 한 단계 이동\r\n* Ctrl + Y : 이후 히스토리로 한 단계 이동\r\n\r\n\r\n# < 그 외 소소한 Tip\'s >\r\n- 파일명은 무조건 대문자, 띄어쓰기는 불가, 문자별 첫문자는 대문자\r\n\r\n- 프로그램의 시작 폴더는 \"Main\" 하나 만들고 시작\r\n\r\n- 왼쪽의 녹색 점(Break point)이 생성되면 다시 더블 클릭해서 삭제 가능(참고로 break point를 찍고 디버깅을 실시하면 그 포인트에서 진행이 걸리게(?) 되는데 소스코드의 문제점 등을 파악할 때 유용하다)\r\n\r\n- System.out 은 출력 개념, System.in은 입력 개념\r\n\r\n',2,1),
(3,'2020-12-17 18:38:39','2020-12-19 17:19:23','2강. Scanner객체를 통한 입력','내가 원하는 명령어 입력하고 이 명령어를 컴퓨터에게 올바르게 인식시키기 위해 JAVA에선 **Scanner 유틸**을 사용한다.\r\n<br>\r\n\r\n별도의 입력없이\r\n**Scanner scanner = new Scanner(System.in);** 라고 작성하면 **Scanner** 부분에 밑줄이 생긴다.\r\n\r\n이때 **Scanner에 커서를 올려두고 \'ctrl+shift+O\'를 누르면,**\r\n\r\n**상단에 import java.util.Scanner; 라고 자동으로 유틸을 불러올 수 있다.**\r\n\r\n(자바에는 사용하기 편리하게 몇가지 유용한 유틸들이 라이브러리에 이미 저장되어 있다.)\r\n<br>\r\n\r\n이후에 예를 들어\r\n\r\nString command = scanner.nextLine(); 라는 객체를 만들고 실행을 하면\r\n\r\n콘솔창에서 명령어를 입력할 수 있게 된다.\r\n<br>\r\n\r\n또한 Scanner 객체를 생성한 후 하단에는 scanner.close();이라고 작성해 주는 것이 좋다.\r\n\r\n(scanner.close();을 꼭 작성하지 않아도 실행하는 데 문제는 없지만 그래도 클린 코드를 위해 작성하도록 하자)\r\n\r\n* * *\r\n\r\n\r\n**예시**\r\n\r\n``` java\r\nimport java.util.Scanner;  //Scanner 유틸을 불러온 것\r\n\r\npublic class Main {\r\n\r\n	public static void main(String[] args) {\r\n		Scanner scanner = new Scanner(System.in);\r\n		\r\n		String command = scanner.nextLine();\r\n		\r\n		scanner.close();   //마지막에는 scanner를 종료시켜주는 것이 좋다\r\n\r\n	}\r\n\r\n}\r\n```',2,1),
(4,'2020-12-20 19:27:12','2020-12-20 19:27:12','3강. println(), printf()를 통한 출력','입력을 했다면 다음은 **출력**이다.\r\n\r\nJAVA에서 출력은 **System.out.println()** 과 **System.out.printf()** 을 활용해 수행할 수 있다.\r\n\r\n이 둘은 비슷하지만 그 사용법과 결과물에서 조금 차이가 있다.\r\n<br>\r\n아래 예시를 통해 그 차이를 살펴보자.\r\n\r\n### 1. System.out.println()\r\n```java\r\n//1.문장 출력\r\nSystem.out.println(\"명령어\");\r\n//출력 결과: 명령어\r\n				\r\n//2.변수 출력\r\nint a = 50;\r\nSystem.out.println(a);\r\n//출력 결과: 50\r\n				\r\n//3.문장 + 변수 출력\r\nSystem.out.println(\"a = \" + a + \"이다.\");\r\n//출력 결과: a = 50이다.\r\n\r\n//4.연속으로 사용하면 아래방향으로 출력이 진행된다.\r\nSystem.out.println(\"명령어1\");\r\nSystem.out.println(\"명령어2\");\r\nSystem.out.println(\"명령어3\");\r\n/*출력 결과: 명령어1\r\n            명령어2\r\n            명령어3    */\r\n```\r\n<br><br>\r\n\r\n### 2. System.out.printf()\r\n```java\r\n//1.문장 출력\r\nSystem.out.printf(\"명령어\");\r\n//출력 결과: 명령어\r\n				\r\n//2.변수 출력\r\nint a = 50;\r\nSystem.out.printf(\"%d\",a);\r\n//출력 결과: 50\r\n\r\n/*\r\n여기서 println()과 다른점은 위와 같이 변수만 입력해서는 출력이 진행되지 않는다는 점이다. \"%d\"와 같이 각 변수 타입에 맞는 명령어를 입력해주어야 그 자리에 원하는 변수 값을 치환시켜 출력할 수 있다.\r\n\r\n-변수 타입과 치환명령어 (예시)\r\n\r\nint => %d\r\nfloat => %f\r\nString => %s\r\n...\r\n이 외에도 다양한 치환명령어가 존재한다.\r\n*/\r\n\r\n				\r\n//3.문장 + 변수 출력\r\nSystem.out.printf(\"a = \" + a + \"이다.\");\r\n//출력 결과: a = 50이다.\r\n\r\n//4.연속으로 사용하면 println()와 다르게 가로방향으로 출력이 진행된다.\r\nSystem.out.printf(\"명령어1\");\r\nSystem.out.printf(\"명령어2\");\r\nSystem.out.printf(\"명령어3\");\r\n//출력 결과: 명령어1명령어2명령어3\r\n\r\n/*\r\n만약 printf()로 println()와 같이 아래방향으로 출력을 진행시키고 싶다면 명령어 뒤에 \"\\n\"을 입력해주면 된다.\r\n\r\nSystem.out.printf(\"명령어1\\n\");\r\nSystem.out.printf(\"명령어2\\n\");\r\nSystem.out.printf(\"명령어3\\n\");\r\n/*출력 결과: 명령어1\r\n            명령어2\r\n            명령어3    */\r\n\r\n*/\r\n```',2,1),
(5,'2020-12-20 19:39:22','2020-12-20 19:39:31','4강. 기본 자료형(데이터 타입)','JAVA에서의 기본 자료형(데이터 타입) 정리한 표이다.\r\nJAVA에서 변수는 매우 중요한 역할을 하므로 이 표를 통해 각 변수의 특징에 대해 알아두는 것이 좋다. \r\n<br>\r\n\r\n#### <기본 자료형>\r\n\r\n<table style=\"border-collapse: collapse; width: 100%; height: 586px;\" border=\"1\" data-ke-style=\"style8\"><tbody><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 69px;\" width=\"85\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>유형</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>종류</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>표현 가능 범위</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>메모리 크기</b></span></p></td></tr><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 300px;\" rowspan=\"4\" width=\"85\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>정수형</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">byte</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">-128 ~ 127</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">1 byte</span></p></td></tr><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">short</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">-32,768 ~ 32,767</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">2 byte</span></p></td></tr><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">Int</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">-2,147,483,648 ~ 2,147,483,647</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">4 byte</span></p></td></tr><tr style=\"height: 93px;\"><td style=\"text-align: center; height: 93px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">long</span></p></td><td style=\"text-align: center; height: 93px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">- 9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807</span></p></td><td style=\"text-align: center; height: 93px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">8 byte</span></p></td></tr><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 79px;\" rowspan=\"2\" width=\"85\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>실수형</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">float</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">1.4E-45 ~ 3.4028235E38</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">4 byte</span></p></td></tr><tr style=\"height: 10px;\"><td style=\"text-align: center; height: 10px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">double<br>(float보다 2배 크기)</span></p></td><td style=\"text-align: center; height: 10px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">4.9E-324 ~ 1.7976931348623157E308</span></p></td><td style=\"text-align: center; height: 10px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">8 byte</span></p></td></tr><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 69px;\" width=\"85\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>문자형</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">char</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">모든 유니코드 문자</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">2 byte</span></p></td></tr><tr style=\"height: 69px;\"><td style=\"text-align: center; height: 69px;\" width=\"85\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\"><b>논리형</b></span></p></td><td style=\"text-align: center; height: 69px;\" width=\"142\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">boolean</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"274\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">true, false</span></p></td><td style=\"text-align: center; height: 69px;\" width=\"101\"><p><span style=\"font-family: \'Noto Sans Demilight\', \'Noto Sans KR\';\">1 byte</span></p></td></tr></tbody></table>',2,1),
(6,'2020-12-22 18:27:10','2020-12-22 18:27:10','YouTube 영상 테스트입니다.','\r\n# 영상 테스트\r\n\r\n```youtube\r\nIUI2O8g2Rs4\r\n```\r\n\r\n출처:몰입코딩',1,1),
(7,'2020-12-22 19:09:39','2020-12-22 19:09:39','5강. 비교연산자','JAVA에서 두 수의 크기를 비교하고 싶을 때에는 **비교연산자**를 사용할 수 있다.\r\n\r\n비교연산자의 종류는 다음과 같다.\r\n\r\n\r\n\"==\",  \"<\",  \"<=\",  \">\",  \">=\",  \"!=\"\r\n\r\n\r\n수학에서 쓰이는 기호와 같다고 생각하면 된다.\r\n<br>\r\n\r\n단, 사용시 주의할 점은 \" = \" 기호를 쓸 때 일반 수학에서는 하나만 입력해도 되지만 JAVA에서는 \" == \" 처럼 2개를 입력해야 한다.\r\n\r\n또한 \" >= \", \" <= \" 기호를 입력할 때에는 반드시 \" > \", \" < \" 기호가 \" = \" 기호 앞에 위치해야 한다는 점을 명심하자.\r\n<br>\r\n\r\n### 예시\r\n\r\n```java\r\nint a = 20;\r\nint b = 20;   \r\n\r\n// a와 b가 같은지 비교하는 연산자\r\n    System.out.println(a == b);    \r\n    // 결과: true\r\n		    \r\n// a가 b보다 작은지 비교하는 연산자\r\n    System.out.println(a < b);\r\n    // 결과: false\r\n		    \r\n// a가 b보다 작거나 같은지 비교하는 연산자\r\n    System.out.println(a <= b);\r\n    // 결과: true\r\n		    \r\n// a가 b보다 큰지 비교하는 연산자\r\n    System.out.println(a > b);      \r\n    // 결과: false\r\n\r\n// a가 b보다 크거나 같은지 비교하는 연산자\r\n    System.out.println(a >= b);     \r\n    // 결과: true\r\n		    \r\n// a와 b가 다른지 비교하는 연산자\r\n    System.out.println(a != b);   \r\n    // 결과: false \r\n```',2,1);

/*Table structure for table `board` */

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `code` char(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `board` */

insert  into `board`(`id`,`name`,`code`) values 
(1,'공지사항','notice'),
(2,'JAVA','java');

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `loginId` char(100) COLLATE utf8_unicode_ci NOT NULL,
  `loginPw` char(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` char(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `member` */

insert  into `member`(`id`,`loginId`,`loginPw`,`name`) values 
(1,'admin','admin1!','Dev_J');

/*Table structure for table `recommand` */

DROP TABLE IF EXISTS `recommand`;

CREATE TABLE `recommand` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `recommandArticleId` int(10) NOT NULL,
  `recommandMemberId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `recommand` */

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `replyBody` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `replyArticleId` int(10) NOT NULL,
  `replyMemberId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `reply` */

/*Table structure for table `view` */

DROP TABLE IF EXISTS `view`;

CREATE TABLE `view` (
  `viewCount` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `viewArticleId` int(10) NOT NULL,
  PRIMARY KEY (`viewCount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `view` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
