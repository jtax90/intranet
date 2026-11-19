package com.intr.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmpDao {
	//
	@Autowired
	SqlSession sqlsession;
	
	// 사원 목록
	public List<HashMap<String, Object>> empInqyDao1010(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.empInqyDao1010", paramMap);
		return defaultList;
	}
	
	// 사용자 정보 조회 (정보 찾기)
	public HashMap<String, Object> empInqyDao1020(HashMap<String, Object> paramMap) {
		HashMap<String, Object> defatulInfo = null;
		defatulInfo = sqlsession.selectOne("emp.empInqyDao1020", paramMap);
		//
		return defatulInfo;
	}
	
	// 관리자 계정 조회
	public HashMap<String, Object> empInqyDao1030() {
		HashMap<String, Object> defatulInfo = null;
		defatulInfo = sqlsession.selectOne("emp.empInqyDao1030");
		//
		return defatulInfo;
	}
	
	// 부서 목록 조회
	public List<HashMap<String, Object>> intrEmpInqyDao1040(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao1040", paramMap);
		return defaultList;
	}
	
	// 직급 목록 조회
	public List<HashMap<String, Object>> intrEmpInqyDao1050(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao1050", paramMap);
		return defaultList;
	}
	
	// 부서 목록 (전체) 조회
	public List<HashMap<String, Object>> intrEmpInqyDao1060(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao1060", paramMap);
		return defaultList;
	}
	
	// 사원 상세 조회
	public HashMap<String, Object> intrEmpInqyDao1070(HashMap<String, Object> paramMap) {
		//
		HashMap<String, Object> defaultInfo = null;
		defaultInfo = sqlsession.selectOne("emp.intrEmpInqyDao1070",paramMap);
		return defaultInfo;
	}
	
	// 사원 아이디 중복 조회
	public List<HashMap<String, Object>> intrEmpInqyDao1080(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao1080", paramMap);
		return defaultList;
	}
	
	// 부서 사원 트리 조회
	public List<HashMap<String, Object>> intrEmpInqyDao1090(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao1090", paramMap);
		return defaultList;
	}
	
	// 부서 현황
	public List<HashMap<String, Object>> intrEmpInqyDao2010(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao2010");
		return defaultList;
	}
	
	// 담당업무 조회
	public List<HashMap<String, Object>> intrEmpInqyDao2020(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao2020", paramMap);
		return defaultList;
	}
	
	// 담당업무 등록 조회
	public HashMap<String, Object> intrEmpInqyDao2030(HashMap<String, Object> paramMap) {
		//
		HashMap<String, Object> defaultInfo = null;
		defaultInfo = sqlsession.selectOne("emp.intrEmpInqyDao2030",paramMap);
		return defaultInfo;
	}
	
	// 입사 현황
	public List<HashMap<String, Object>> intrEmpInqyDao3010(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao3010", paramMap);
		return defaultList;
	}
	
	// 인사통계 목록 조회
	public List<HashMap<String, Object>> intrEmpInqyDao3020(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao3020", paramMap);
		return defaultList;
	}
	
	// 통계 연도 조회
	public List<HashMap<String, Object>> intrEmpInqyDao3030(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao3030", paramMap);
		return defaultList;
	}
	
	// 통계 연월 조회
	public List<HashMap<String, Object>> intrEmpInqyDao3040(HashMap<String, Object> paramMap) {
		//
		List<HashMap<String, Object>> defaultList = null;
		defaultList = sqlsession.selectList("emp.intrEmpInqyDao3040", paramMap);
		return defaultList;
	}
	
	// 사원 등록
	public int intrEmpProcDao1010(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.insert("emp.intrEmpProcDao1010", paramMap);
		return resInt;
	}
	
	// 사원 수정
	public int intrEmpProcDao1020(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.update("emp.intrEmpProcDao1020", paramMap);
		return resInt;
	}
	
	// 사원 복직, 퇴사 처리
	public int intrEmpProcDao1030(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.update("emp.intrEmpProcDao1030", paramMap);
		return resInt;
	}
	
	// 사원 삭제 처리
	public int intrEmpProcDao1040(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.update("emp.intrEmpProcDao1040", paramMap);
		return resInt;
	}
	
	// 사원  비밀번호 수정
	public int intrEmpProcDao1050(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.update("emp.intrEmpProcDao1050", paramMap);
		return resInt;
	}

	// 담당업무 저장 처리
	public int intrEmpProcDao1060(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.update("emp.intrEmpProcDao1060", paramMap);
		return resInt;
	}
	
	// 담당업무 삭제 처리
	public int intrEmpProcDao1070(HashMap<String, Object> paramMap) {
		//
		int resInt = 0;
		resInt = sqlsession.delete("emp.intrEmpProcDao1070", paramMap);
		return resInt;
	}
}
