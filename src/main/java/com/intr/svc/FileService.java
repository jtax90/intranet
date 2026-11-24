package com.intr.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface FileService {
	// 파일 경로 생성
	String getFilePath(HashMap<String, Object> paramMap) throws Exception; 	// 일반 경로
	String getTempPath(HashMap<String, Object> paramMap) throws Exception; // 임시 경로
	String getBasePath(String basePath, String filetypeCd) throws Exception; 	// 기본 경로
	
	// 파일 업로드
	String fileUpload(Model model, HashMap<String, Object> paramMap, MultipartHttpServletRequest request) throws Exception; // 업로드

	// 다운로드
	void fileDown(Model model, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception; // 단건
	void manualDown(Model model, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception; // 참고문서
	void zipDown(Model model, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception; // 전체
	void excelDown(List<Map<String, Object>> defaultList, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception; // 엑셀

}
