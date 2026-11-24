package com.intr.svc;

import java.util.HashMap;

import org.springframework.ui.Model;

public interface UtilService {
	// 공통
	boolean isNull(String param) throws Exception;
	String nvlProc(String param) throws Exception;
	
	// 페이지
	void setPaging(Model model, HashMap<String, Object> paramMap) throws Exception; // 페이징 처리
	String setPageMenu(HashMap<String, Object> paramMap) throws Exception; // 페이징 목록 생성
	String setPageUrl(HashMap<String, Object> paramMap) throws Exception; // 페이지 URL 생성
	
	// 코드 조회
	void getCodeList(Model model, String groupCd, String modelNm) throws Exception;
	
	// 암복호화
	String encryptProc(String encryptedText) throws Exception;
	String decryptProc(String encryptedText) throws Exception;
}
