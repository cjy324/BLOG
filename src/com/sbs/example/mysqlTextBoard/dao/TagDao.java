package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Tag;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class TagDao {

	// relTypeCode에 관련된 태그 리스트 가져오기
	public List<Tag> getTagByRelTypeCode(String relTypeCode) {
		SecSql sql = new SecSql();

		sql.append("SELECT T.*");
		sql.append("FROM tag AS T");
		sql.append("WHERE 1");   //1은 "참"이라는 의미

		if(relTypeCode != null && relTypeCode.length() > 0) {
			sql.append("AND T.relTypeCode = ?", relTypeCode);
		}
		
		sql.append("ORDER BY T.body ASC");

		List<Tag> tags = new ArrayList<>();
		List<Map<String, Object>> tagsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> tagsMap : tagsMapList) {
			Tag tag = new Tag(tagsMap);

			tags.add(tag);

		}
		// Collections.reverse(articles);
		return tags;
	}

	// 중복이 제거된 태그 body 리스트 가져오기
	public List<String> getDedupTagBodiesByRelTypeCode(String relTypeCode) {
		SecSql sql = new SecSql();

		sql.append("SELECT T.body");
		sql.append("FROM tag AS T");
		sql.append("WHERE 1");   //1은 "참"이라는 의미

		if(relTypeCode != null && relTypeCode.length() > 0) {
			sql.append("AND T.relTypeCode = ?", relTypeCode);
		}
		
		sql.append("GROUP BY T.body");
		sql.append("ORDER BY T.body ASC");

		List<String> tagBodies = new ArrayList<>();
		List<Map<String, Object>> tagsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> tagsMap : tagsMapList) {

			tagBodies.add((String) tagsMap.get("body"));

		}
		// Collections.reverse(articles);
		return tagBodies;
		
	}

}
