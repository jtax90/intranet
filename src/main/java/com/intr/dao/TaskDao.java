package com.intr.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDao {
	//
	@Autowired
	SqlSession sqlsession;
	
	// 업무일지 작성 조회
	public List<HashMap<String, Object>> taskInqyDao1010(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("task.taskInqyDao1010", paramMap);
		return defaultList;
	}

	// 업무일지 목록 조회
	public List<HashMap<String, Object>> taskInqyDao2010(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("task.taskInqyDao2010", paramMap);
		return defaultList;
	}
	
	// 업무일지 상세 조회
	public List<HashMap<String, Object>> taskInqyDao2020(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("task.taskInqyDao2020", paramMap);
		return defaultList;
	}
	
	// 업무일지 등록
	public int taskProcDao1010(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.insert("task.taskProcDao1010", paramMap);
		return resInt;
	}
	
	// 업무일지 삭제
	public int taskProcDao1020(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.delete("task.taskProcDao1020", paramMap);
		return resInt;
	}
}
