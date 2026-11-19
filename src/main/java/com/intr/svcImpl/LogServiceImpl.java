package com.intr.svcImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.intr.dao.LogDao;
import com.intr.dao.UtilDao;
import com.intr.svc.LogService;
import com.intr.svc.UtilService;

@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl implements LogService {
	//
	@Autowired
	UtilService utilService;
	
	@Autowired
	UtilDao utilDao;
	
	@Autowired
	LogDao logDao;
	
	// 로그 목록 조회
	public void logInqyService1010(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 페이징 처리
			//--------------------------------------------------------------------------------------------
			utilService.setPaging(model, paramMap);

			//--------------------------------------------------------------------------------------------
			// 로그 목록 조회
			//--------------------------------------------------------------------------------------------
			defaultList = logDao.logInqyDao1010(paramMap);
			model.addAttribute("defaultList",defaultList);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}

	@Override
	public void logProcService1010(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 로그 등록
			//--------------------------------------------------------------------------------------------
			logDao.logProcDao1010(paramMap);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}
}
