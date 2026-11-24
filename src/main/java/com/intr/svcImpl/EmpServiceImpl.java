package com.intr.svcImpl;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.intr.dao.EmpDao;
import com.intr.dao.FileDao;
import com.intr.dao.MainDao;
import com.intr.svc.EmpService;
import com.intr.svc.FileService;
import com.intr.svc.MainService;
import com.intr.svc.UtilService;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmpServiceImpl implements EmpService {
	//
	@Autowired
	MainService mainService;

	@Autowired
	UtilService utilService;

	@Autowired
	FileService fileService;
	
	@Autowired
	MainDao mainDao;

	@Autowired
	EmpDao empDao;

	@Autowired
	FileDao fileDao;
	
	@Autowired
	HttpServletRequest request;

	// 사원 목록 조회
	public void empInqyService1010(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 페이징 처리
			// --------------------------------------------------------------------------------------------
			utilService.setPaging(model, paramMap);

			// --------------------------------------------------------------------------------------------
			// 사원 목록
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.empInqyDao1010(paramMap);
			model.addAttribute("defaultList", defaultList);

			// --------------------------------------------------------------------------------------------
			// 공통코드 (재직여부) 조회
			// --------------------------------------------------------------------------------------------
			utilService.getCodeList(model, "Hire", "hireList");

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 부서 직급 조회
	public void empInqyService1020(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 부서 목록 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao1040(paramMap);
			model.addAttribute("orgList", defaultList);

			// --------------------------------------------------------------------------------------------
			// 직급 목록 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao1050(paramMap);
			model.addAttribute("rankList", defaultList);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 사원 상세 조회
	public void empInqyService1030(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		HashMap<String, Object> defaultInfo = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 사원 상세 정보
			// --------------------------------------------------------------------------------------------
			defaultInfo = empDao.intrEmpInqyDao1070(paramMap);
			model.addAttribute("defaultInfo", defaultInfo);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 부서 직급 조회
	public void empInqyService1040(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 부서 목록 (전체) 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao1060(paramMap);
			model.addAttribute("orgList", defaultList);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 사원 아이디 중복 조회
	public String empInqyService2020(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		String defaultStr = "";
		String resStr = "NO";
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 사원 아이디 중복 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao1080(paramMap);
			//
			if (defaultList.size() > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}
		//
		return defaultStr;
	}

	// 부서 사원 트리 조회
	public void empInqyService2030(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 부서 사원 트리 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao1090(paramMap);
			model.addAttribute("defaultList", defaultList);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 부서 현황
	public void empInqyService2040(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 부서 현황
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao2010(paramMap);
			model.addAttribute("empCnt", defaultList);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 담당업무 조회
	public void empInqyService3010(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 페이징 처리
			// --------------------------------------------------------------------------------------------
			utilService.setPaging(model, paramMap);

			// --------------------------------------------------------------------------------------------
			// 담당업무 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao2020(paramMap);
			model.addAttribute("defaultList", defaultList);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 담당업무 조회 (AJAX)
	public void empInqyService3011(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 페이징 처리
			// --------------------------------------------------------------------------------------------
			utilService.setPaging(model, paramMap);

			// --------------------------------------------------------------------------------------------
			// 담당업무 조회
			// --------------------------------------------------------------------------------------------
			defaultList = empDao.intrEmpInqyDao2020(paramMap);
			model.addAttribute("defaultList", defaultList);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 담당업무 등록 조회 (AJAX)
	public void empInqyService3012(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		HashMap<String, Object> defaultInfo = null;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 담당업무 등록 조회
			// --------------------------------------------------------------------------------------------
			defaultInfo = empDao.intrEmpInqyDao2030(paramMap);
			model.addAttribute("defaultInfo", defaultInfo);

		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 사원 등록
	public String empProcService1010(Model model, HashMap<String, Object> paramMap, MultipartHttpServletRequest request) throws Exception {
		//
		String defaultStr = "";
		String resStr = "NO";
		String fileId = "";
		int resInt = 0;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 비밀번호 암호화
			// --------------------------------------------------------------------------------------------
			paramMap.put("empPwd", utilService.encryptProc((String) paramMap.get("empPwd")));
			
			// --------------------------------------------------------------------------------------------
			// 파일 등록
			// --------------------------------------------------------------------------------------------
			fileId = fileService.fileUpload(model, paramMap, request);
			paramMap.put("fileId", fileId);

			// --------------------------------------------------------------------------------------------
			// 사원 등록
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1010(paramMap);
			//
			if (resInt > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}

		return defaultStr;
	}

	// 사원 수정
	public String empProcService1020(Model model, HashMap<String, Object> paramMap, MultipartHttpServletRequest request) throws Exception {
		//
		HashMap<String, Object> defaultInfo = null;
		String defaultStr = "";
		String resStr = "NO";
		String fileId = "";
		int resInt = 0;
		String empPwd = utilService.nvlProc((String) paramMap.get("empPwd"));
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 기존 사원 정보 조회
			// --------------------------------------------------------------------------------------------
			defaultInfo = empDao.intrEmpInqyDao1070(paramMap);
			paramMap.put("fileId", defaultInfo.get("fileId"));
			
			// --------------------------------------------------------------------------------------------
			// 비밀번호 암호화
			// --------------------------------------------------------------------------------------------
			if (!empPwd.isEmpty()) {
				paramMap.put("empPwd", utilService.encryptProc((String) paramMap.get("empPwd")));
			}
			
			// --------------------------------------------------------------------------------------------
			// 이미지 수정
			// --------------------------------------------------------------------------------------------
			fileId = fileService.fileUpload(model, paramMap, request);
			paramMap.put("fileId", fileId);

			// --------------------------------------------------------------------------------------------
			// 사원 수정
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1020(paramMap);
			//
			if (resInt > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}

		return defaultStr;
	}

	// 사원 복직, 퇴사 처리
	public String empProcService1030(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		String defaultStr = "";
		String resStr = "NO";
		int resInt = 0;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 사원 복직, 퇴사 처리
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1030(paramMap);
			//
			if (resInt > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}

		return defaultStr;
	}

	// 사원 삭제
	public String empProcService1040(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		HashMap<String, Object> defaultInfo = null;
		HashMap<String, Object> fileMap = new HashMap<String, Object>();
		String defaultStr = "";
		String resStr = "NO";
		String fileId = "";
		int resInt = 0;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 기존 사원 정보 조회
			// --------------------------------------------------------------------------------------------
			defaultInfo = empDao.intrEmpInqyDao1070(paramMap);
			fileId = utilService.nvlProc((String) defaultInfo.get("fileId"));
			
			// --------------------------------------------------------------------------------------------
			// 사원 삭제
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1040(paramMap);
			//
			if (resInt > 0) {
				// 이미지 삭제
				if (!fileId.isEmpty()) {
					fileMap.put("fileId", fileId);
					fileDao.fileProcDao1030(fileMap);
				}
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}

		return defaultStr;
	}

	// 사원 비밀번호 수정
	public String empProcService1050(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		String defaultStr = "";
		String resStr = "NO";
		String encryptedText = "";
		String newPwd = utilService.nvlProc((String) paramMap.get("newPwd"));
		int resInt = 0;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 암호화 처리
			// --------------------------------------------------------------------------------------------
			encryptedText = utilService.encryptProc(newPwd);
			paramMap.put("chngPwd", encryptedText);

			// --------------------------------------------------------------------------------------------
			// 사원 수정
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1050(paramMap);
			//
			if (resInt > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}

		return defaultStr;
	}

	// 담당업무 저장 처리
	public String empProcService2010(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		String defaultStr = "";
		String resStr = "NO";
		int resInt = 0;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 담당업무 저장 처리
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1060(paramMap);
			//
			if (resInt > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}

		return defaultStr;
	}

	// 담당업무 삭제 처리
	public String empProcService2020(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		String defaultStr = "";
		String resStr = "NO";
		int resInt = 0;
		//
		try {
			// --------------------------------------------------------------------------------------------
			// 담당업무 삭제 처리
			// --------------------------------------------------------------------------------------------
			resInt = empDao.intrEmpProcDao1070(paramMap);
			//
			if (resInt > 0) {
				resStr = "YES";
			}

			// --------------------------------------------------------------------------------------------
			// 결과 반환
			// --------------------------------------------------------------------------------------------
			defaultStr = String.format("[{'res':'%s'}]", resStr);

		} catch (Exception e) {
			//
			throw e;
		}
		//
		return defaultStr;
	}
}
