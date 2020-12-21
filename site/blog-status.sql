## 사이트 운영 시작 ##

#기존 사이트 게시글 및 사용자 초기화 
TRUNCATE `article`;
TRUNCATE `member`;
TRUNCATE `board`;

# 관리자 생성
SELECT * FROM `member`;
INSERT INTO `member`
SET loginId = 'admin',
loginPw = 'admin1!',
`name` = 'Dev_J';


# JAVA 관련 게시판 생성
INSERT INTO `board`
SET `name` = '공지사항',
`code` = 'notice';

INSERT INTO `board`
SET `name` = 'JAVA',
`code` = 'java';

SELECT * FROM `article`;
SELECT * FROM `member`;
SELECT * FROM `board`;

