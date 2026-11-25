package com.intr.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {
	//
	@Autowired
	SqlSession sqlsession;
	
	// 파일 채번
	public HashMap<String, Object> fileInqyDao1010(HashMap<String, Object> paramMap) {
		// 
		HashMap<String, Object> defaultInfo = null;
		defaultInfo = sqlsession.selectOne("file.fileInqyDao1010",paramMap);
		return defaultInfo;
	}
	
	// 다건 파일 조회
	public List<HashMap<String, Object>> fileInqyDao1020(String string) {
		// 
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("file.fileInqyDao1020",string);
		return defaultList;
	}
	
	// 단건 파일 조회
	public HashMap<String, Object> fileInqyDao1030(HashMap<String, Object> paramMap) {
		// 
		HashMap<String, Object> defaultInfo = null;
		defaultInfo = sqlsession.selectOne("file.fileInqyDao1030",paramMap);
		return defaultInfo;
	}
	
	// 파일 등록 처리
	public int fileProcDao1010(HashMap<String, Object> tempMap) {
		//
		int res = 0;
		res = sqlsession.insert("file.fileProcDao1010", tempMap);
		return res;
	}
	
	// 파일 삭제 처리 (수정)
	public int fileProcDao1020(HashMap<String, Object> paramMap) {
		//
		int res = 0;
		res = sqlsession.update("file.fileProcDao1020", paramMap);
		return res;
	}
	
	// 파일 삭제 처리 (삭제)
	public int fileProcDao1030(HashMap<String, Object> paramMap) {
		//
		int res = 0;
		res = sqlsession.delete("file.fileProcDao1030", paramMap);
		return res;
	}
}
