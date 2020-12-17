DROP DATABASE IF EXISTS textBoard;
CREATE DATABASE textBoard;
USE textBoard;

CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    loginId CHAR(100) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    `name` CHAR(100) NOT NULL
);

SELECT * FROM `member`;

INSERT INTO `member`
SET loginId = 'asd',
loginPw = 'asd',
`name` = '테스트 관리자';

INSERT INTO `member`
SET loginId = 'asdf',
loginPw = 'asdf',
`name` = '아무개';

SELECT * FROM `member`;

CREATE TABLE `board`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` CHAR(100) NOT NULL,
    `code` CHAR(100) NOT NULL
);

SELECT * FROM `board`;

INSERT INTO `board`
SET `name` = '공지사항',
`code` = 'notice';

INSERT INTO `board`
SET `name` = '자유',
`code` = 'free';

SELECT * FROM `board`;

CREATE TABLE `article`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL,
    boardId INT(10) NOT NULL,
    memberId INT(10) NOT NULL
);

SELECT * FROM `article`;

INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = '테스트 제목1',
`body` = '테스트 내용1',
`boardId` = 1,
`memberId` = 1;

INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = '테스트 제목2',
`body` = '테스트 내용2',
`boardId` = 1,
`memberId` = 1;

INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = '테스트 제목3',
`body` = '테스트 내용3',
`boardId` = 1,
`memberId` = 1;


SELECT * FROM `article`;

CREATE TABLE `reply`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,    
    replyBody TEXT NOT NULL,
    replyArticleId INT(10) NOT NULL,
    replyMemberId INT(10) NOT NULL
);

SELECT * FROM `reply`;

CREATE TABLE `recommand`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,    
    recommandArticleId INT(10) NOT NULL,
    recommandMemberId INT(10) NOT NULL
);

SELECT * FROM `recommand`;

CREATE TABLE `view`(
    viewCount INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    viewArticleId INT(10) NOT NULL
);

SELECT * FROM `view`;

#게시물 랜덤 생성
INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = CONCAT('제목_',RAND()),
`body` = CONCAT('내용_',RAND()),
`boardId` = FLOOR(RAND()*2) + 1,
`memberId` = FLOOR(RAND()*2) + 1;

#게시물 랜덤 생성
INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = CONCAT('제목_',RAND()),
`body` = CONCAT('내용_',RAND()),
`boardId` = FLOOR(RAND()*2) + 1,
`memberId` = FLOOR(RAND()*2) + 1;

# 1번글 내용에 마크다운 넣기
UPDATE `article` 
SET `title` = '마크다운 적용 확인용 공지',
`body` = '# 마크다운 적용 확인용 내용 \n ## 마크다운 적용 확인용 내용 \n  - 마크다운 적용 확인용 내용'
WHERE `id` = '1'; 

# 2번글 내용에 자바소스코드 넣기
UPDATE article 
SET `title` = '하이라이트 적용 확인용 공지2',
`body` = '# 자바기본문법\r\n```java\r\nint a = 10;\r\nint b = 20;\r\nint c = a + b;\r\n```'
WHERE id = '2'; 

SELECT * FROM `article`;
