package com.intr.svc;

import java.util.HashMap;

import org.springframework.ui.Model;

public interface TaskService {
	// 업무
	void taskInqyService1010(Model model, HashMap<String, Object> paramMap) throws Exception; 		// 업무 작성
	void taskInqyService1020(Model model, HashMap<String, Object> paramMap) throws Exception; 		// 업무 작성 (AJAX)
	void taskInqyService2010(Model model, HashMap<String, Object> paramMap) throws Exception; 		// 업무 조회
	void taskInqyService2020(Model model, HashMap<String, Object> paramMap) throws Exception; 		// 업무 상세
	//
	String taskProcService1010(Model model, HashMap<String,Object> paramMapt) throws Exception; 	// 업무일지 저장
	String taskProcService1020(Model model, HashMap<String,Object> paramMapt) throws Exception; 	// 업무일지 삭제
}
