package com.intr.ctr;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.intr.svc.CoreService;
import com.intr.svc.StatsService;
import com.intr.utils.Const;

@Controller
public class StatsController {
	@Autowired
	CoreService coreService;

	@Autowired
	StatsService statsService;
	//
	private final Logger logger = LoggerFactory.getLogger(getClass());

	// 입·퇴사 현황 조회
	@RequestMapping("/intrStatsInqy1010.do")
	public String intrStatsInqy1010(Model model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		try {
			//--------------------------------------------------------------------------------------------
			// 메뉴 조회
			//--------------------------------------------------------------------------------------------
			coreService.coreInqyService1010(model, paramMap);

			//--------------------------------------------------------------------------------------------
			// 입·퇴사 현황 조회
			//--------------------------------------------------------------------------------------------
			statsService.statsInqyService1010(model, paramMap);
			
		} catch (Exception e) {
			//
			logger.debug("Exception : 입·퇴사 현황 조회 중 에러가 발생했습니다. (" + e.getMessage() + ")");
			throw e;
		}
		//
		return Const.VIEW_PATH_STATS + Const.INTR_STATS_LIST_1010;
	}
}