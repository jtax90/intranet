package com.intr.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatsDao {
	@Autowired
	SqlSession sqlsession;

	// 입·퇴사 현황 (메인)
	public List<HashMap<String, Object>> statsInqyDao1010(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("stats.statsInqyDao1010", paramMap);
		return defaultList;
	}

	// 입·퇴사 현황 목록 조회
	public List<HashMap<String, Object>> statsInqyDao1020(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("stats.statsInqyDao1020", paramMap);
		return defaultList;
	}

	// 통계 연도 조회
	public List<HashMap<String, Object>> statsInqyDao1030(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("stats.statsInqyDao1030", paramMap);
		return defaultList;
	}

	// 통계 연월 조회
	public List<HashMap<String, Object>> statsInqyDao1040(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("stats.statsInqyDao1040", paramMap);
		return defaultList;
	}
}