package com.intr.svcImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.intr.dao.EmpDao;
import com.intr.dao.StatsDao;
import com.intr.svc.StatsService;
import com.intr.svc.UtilService;

@Service
@Transactional(rollbackFor = Exception.class)
public class StatsServiceImpl implements StatsService {
	@Autowired
	StatsDao statsDao;

	@Autowired
	EmpDao empDao;

	@Autowired
	UtilService utilService;

	// 입·퇴사 현황 조회
	public void statsInqyService1010(Model model, HashMap<String, Object> paramMap) throws Exception {
		List<HashMap<String, Object>> defaultList = null;
		HashMap<String, Object> statList = new HashMap<String, Object>();
		String key = "";
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 입·퇴사 현황 목록 조회
			// --------------------------------------------------------------------------------------------
			defaultList = statsDao.statsInqyDao1020(paramMap);
			for (HashMap<String, Object> row : defaultList) {
				key = row.get("orgNm") + "_" + row.get("yearMm");
				statList.put(key, row);
			}
			model.addAttribute("statList", statList);

			
			// --------------------------------------------------------------------------------------------
			// 통계 연도 조회
			// --------------------------------------------------------------------------------------------
			defaultList = statsDao.statsInqyDao1030(paramMap);
			model.addAttribute("yearList", defaultList);

			// --------------------------------------------------------------------------------------------
			// 공통코드 (재직여부) 조회
			// --------------------------------------------------------------------------------------------
			utilService.getCodeList(model, "Hire", "hireList");

			// --------------------------------------------------------------------------------------------
			// 통계 연월 조회
			// --------------------------------------------------------------------------------------------
			defaultList = statsDao.statsInqyDao1040(paramMap);
			model.addAttribute("ymList", defaultList);

			// --------------------------------------------------------------------------------------------
			// 부서 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao1040(paramMap);
			model.addAttribute("orgList", defaultList);
			
		} catch (Exception e) {
			throw e;
		}
	}
}