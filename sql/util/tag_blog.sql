USE textBoard;

# 변수 세팅 필요 시작 #
SET @articleId = 26;
SET @hashsStr = '#project #개발일지 #JSP';
# 변수 세팅 필요 끝 #

# 해시태그 구분자
SET @hashSign = '#';

# 새 해시태그 개수
SET @hashsCount = LENGTH(@hashsStr) - LENGTH(REPLACE(@hashsStr, @hashSign, ""));
SET @hashsCount = @hashsCount / LENGTH(@hashSign);
SET @hashsCount = FLOOR(@hashsCount);

SELECT @hashsCount;

# 삭제되어야 할 것 삭제, v2
DELETE FROM tag
WHERE id IN (
    SELECT OT.id
    FROM tag AS OT
    LEFT JOIN (
        WITH RECURSIVE rgen(n) AS (
            SELECT 1
            UNION ALL
            SELECT n + 1
            FROM rgen
            WHERE n < @hashsCount
        )
        SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(@hashsStr, @hashSign, n + 1), @hashSign, -1)) AS `body`
        FROM rgen
    ) AS NT
    ON OT.body = NT.body
    WHERE NT.body IS NULL
    AND relTypeCode = 'article'
    AND relId = @articleId
);

# 추가 되어야 하는 녀석들 추가
INSERT INTO tag
(regDate, updateDate, relTypeCode, relId, `body`)
SELECT NOW(), NOW(), 'article', @articleId, NT.body
FROM (
    WITH RECURSIVE rgen(n) AS (
        SELECT 1
        UNION ALL
        SELECT n + 1
        FROM rgen
        WHERE n < @hashsCount
    )
    SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(@hashsStr, @hashSign, n + 1), @hashSign, -1)) AS `body`
    FROM rgen
) AS NT
LEFT JOIN tag AS OT
ON OT.relTypeCode = 'article'
AND OT.relId = @articleId
AND NT.body = OT.body
WHERE OT.id IS NULL
AND NT.body != '';