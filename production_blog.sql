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

# 공지사항 게시판 생성
INSERT INTO `board`
SET `name` = '공지사항',
`code` = 'notice';

# JAVA 관련 게시판 생성
INSERT INTO `board`
SET `name` = 'JAVA',
`code` = 'java';

# HTML & CSS & JS 관련 게시판 생성
INSERT INTO `board`
SET `name` = 'HTML & CSS & JS',
`code` = 'html';

SELECT * FROM `article`;
SELECT * FROM `member`;
SELECT * FROM `board`;
SELECT * FROM ga4DataPagePath;
SELECT * FROM tag;


SELECT COUNT(id)
FROM `board`;

SELECT COUNT(id)
FROM `article`;

# 전체게시물 + 태그정보 조회
SELECT A.id,
A.title,
IFNULL(GROUP_CONCAT(T.body), '없음') AS tags
FROM article AS A
LEFT JOIN tag AS T
ON A.id = T.relId
AND T.relTypeCode = 'article'
GROUP BY A.id

#GROUP_CONCAT - 구분자를 활용한 문자열 묶음
#GROUP BY로 된 레코드들 중에서 구분자를 이용하여 해당하는 문자열을 보고자 할때 사용
#기본적으로 GROUP BY를 하게되면 여러개의 레코드가 합쳐져도 문자열은 하나만 보이게 된다.
#GROUP_CONCAT(컬럼명)을 하면 각 레코드안에 있는 모든 문자열들이 뒤따라 보이게 된다. 

#IFNULL(칼럼명, '원하는 대답')
#만약 해당 칼럼명안의 데이터가 null이면 '원하는 대답'이 나타나게 한다.

# 게시물 + 태그정보 조회
SELECT
IFNULL(GROUP_CONCAT(T.body), '없음') AS tags
FROM article AS A
LEFT JOIN tag AS T
ON A.id = T.relId
AND T.relTypeCode = 'article'
WHERE A.id = 12
GROUP BY A.id