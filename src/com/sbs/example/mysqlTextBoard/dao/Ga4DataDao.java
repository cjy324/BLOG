package com.sbs.example.mysqlTextBoard.dao;

import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class Ga4DataDao {

	public void deletePageData(String pagePath) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM ga4DataPagePath");
		sql.append("WHERE pagePath = ?", pagePath);

		MysqlUtil.delete(sql);
		
	}

	public void savePageData(String pagePath, int hit) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO ga4DataPagePath");
		sql.append("SET ");
		sql.append("regDate = NOW(), ");
		sql.append("updateDate = NOW(), ");
		sql.append("pagePath = ?, ", pagePath);
		sql.append("hit = ?", hit);

		MysqlUtil.insert(sql);
		
	}


}
