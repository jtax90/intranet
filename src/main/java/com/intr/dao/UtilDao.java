package com.intr.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UtilDao {
	//
	@Autowired
	SqlSession sqlsession;
	
	// 공통코드 조회
	public List<HashMap<String, Object>> codeInqyDao1010(HashMap<String, Object> paramMap) {
		// 
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("util.codeInqyDao1010",paramMap);
		return defaultList;
	}
}
