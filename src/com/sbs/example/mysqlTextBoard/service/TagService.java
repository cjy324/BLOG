package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.TagDao;
import com.sbs.example.mysqlTextBoard.dto.Tag;

public class TagService {

	TagDao tagDao;

	public TagService() {
		tagDao = Container.tagDao;

	}

	// relTypeCode가 article인 태그 리스트만 가져오기(일단 안쓰임)
	public List<Tag> getTagByRelTypeCode(String relTypeCode) {
		return tagDao.getTagByRelTypeCode(relTypeCode);
	}

	// 중복이 제거된 태그 body 리스트 가져오기
	public List<String> getDedupTagBodiesByRelTypeCode(String relTypeCode) {
		return tagDao.getDedupTagBodiesByRelTypeCode(relTypeCode);
	}

}
