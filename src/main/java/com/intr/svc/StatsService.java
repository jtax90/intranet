package com.intr.svc;

import java.util.HashMap;

import org.springframework.ui.Model;

public interface StatsService {
	// 입·퇴사 현황 조회
	void statsInqyService1010(Model model, HashMap<String, Object> paramMap) throws Exception;
}