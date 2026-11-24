package com.intr.svcImpl;

import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.intr.dao.CoreDao;
import com.intr.dao.EmpDao;
import com.intr.dao.QueryDao;
import com.intr.dao.UtilDao;
import com.intr.svc.UtilService;
import com.intr.utils.AESCrypto;
import com.intr.utils.Const;

@Service
@Transactional(rollbackFor = Exception.class)
public class UtilServiceImpl implements UtilService{
	//
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	UtilDao utilDao;

	@Autowired
	CoreDao coreDao;
	
	@Autowired
	EmpDao empDao;
	
	@Autowired
	QueryDao queryDao;
	
	// 코드 조회
	public void getCodeList(Model model, String groupCd, String modelNm) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 코드 조회
			//--------------------------------------------------------------------------------------------
			tempMap.put("grpKey", groupCd);
			defaultList = utilDao.codeInqyDao1010(tempMap);
			model.addAttribute(modelNm, defaultList);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}
	
	// 페이징 처리
	public void setPaging(Model model, HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		String listPage = (String) paramMap.get("page");
		String listCnt = "0";
		//
		int nowPage = 1; // 페이지 기본 값 (첫 동작)
		Integer page = null; // 현재 페이지
		
		if(!this.isNull(listPage)) {
			page = Integer.parseInt(listPage);
		}
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 목록 건수 조회
			//--------------------------------------------------------------------------------------------
			defaultList = coreDao.coreInqyDao1030(paramMap);
			//
			if(defaultList!=null && !defaultList.isEmpty()) {
				listCnt = String.valueOf(defaultList.get(0).get("listCnt"));
			}
			
			// 건수 저장
			paramMap.put("listCnt", listCnt);
			
			//--------------------------------------------------------------------------------------------
			// 페이징 처리
			//--------------------------------------------------------------------------------------------
			if(page!=null) {
				nowPage = page;
			}
			if(nowPage < 1) {
				nowPage = 1;
			}
					
			// 페이지 첫, 마지막 인덱스
			int sIdx = (nowPage-1)*Const.BLOCKLIST+1;
			int eIdx = sIdx+Const.BLOCKLIST-1;
			//
			//--------------------------------------------------------------------------------------------
			// 페이지 변수 저장
			//--------------------------------------------------------------------------------------------
			paramMap.put("nowPage", nowPage);
			paramMap.put("sIdx",sIdx);
			paramMap.put("eIdx",eIdx);
			paramMap.put("blockList",Const.BLOCKLIST);
			paramMap.put("blockPage",Const.BLOCKPAGE);

			// 페이지 메뉴 구성
			String pageMenu = this.setPageMenu(paramMap);
			model.addAttribute("pageMenu", pageMenu);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}
	
	// 페이징 메뉴 생성
	public String setPageMenu(HashMap<String, Object> paramMap) throws Exception {
		//--------------------------------------------------------------------------------------------
		// 변수
		//--------------------------------------------------------------------------------------------
		String pageUrl = this.setPageUrl(paramMap);
		//
		Integer nowPage = Integer.valueOf(String.valueOf(paramMap.get("nowPage")));
		Integer listCnt = Integer.valueOf(String.valueOf(paramMap.get("listCnt"))); 
		Integer blockList = Integer.valueOf(String.valueOf(paramMap.get("blockList")));
		Integer blockPage = Integer.valueOf(String.valueOf(paramMap.get("blockPage")));
		//
		boolean isPrevPage,isNextPage;
		StringBuffer sb; 
		//
		int totalPage,	/* 전체 페이지수 */
            startPage,		/* 시작 페이지 번호 */
            endPage;		/* 마지막 페이지 번호 */
		
		// 전체 페이지 수
		isPrevPage=isNextPage=false;
		totalPage = (int)(listCnt/blockList);
		if(listCnt%blockList!=0)totalPage++;
		
		// 현재 페이지 수가 전체 페이지 수를 초과할 경우 현재 페이지값을 전체 페이지 값으로 변경
		if(nowPage > totalPage)nowPage = totalPage;
		
		// 시작 페이지와 마지막 페이지
		startPage = (int)(((nowPage-1)/blockPage)*blockPage+1);
		endPage = startPage + blockPage - 1; 
		// 
		if(endPage > totalPage)endPage = totalPage; // 마지막 페이지 수가 전체 페이지 수보다 크면 마지막 페이지 값을 변경
		if(endPage < totalPage)isNextPage = true; // 마지막 페이지가 전체 페이지보다 작을 경우 다음 페이징이 적용할 수 있도록 변수 값 설정
		if(startPage > 1)isPrevPage = true; // 시작 페이지의 값이 1보다 작으면 이전 페이지 적용
		
		// HTML 코드 작성
		sb = new StringBuffer();

		//--------------------------------------------------------------------------------------------
		// < (이전) 표시 페이지 처리
		//--------------------------------------------------------------------------------------------
		if(isPrevPage){
			sb.append("<a class=\"pageBtn _prev\" href='"+pageUrl+"&page=");
			sb.append( startPage-1 );
			sb.append("'>이전 페이지로 이동</a>");
		} else {
			// < (클릭이 되지 않음)
			sb.append("<a class=\"pageBtn _first\" href=\"#none\">이전 페이지로 이동</a>");
		}
		//--------------------------------------------------------------------------------------------
		// 페이지 목록 출력
		//--------------------------------------------------------------------------------------------
		sb.append("&nbsp;");
		for(int i=startPage; i<= endPage ;i++){
			if(i>totalPage)break;
			// 현재 선택된 페이지
			if(i == nowPage){ 
				sb.append("<li class=\"_active\"><a>");
				sb.append(i); // 페이지 숫자
				sb.append("</a></li>");
			} else {
				// 선택되지 않은 페이지
				sb.append("<li class=\"\"><a href='"+pageUrl+"&page=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
		//
		sb.append("&nbsp;&nbsp;");
		
		//--------------------------------------------------------------------------------------------
		// > (다음) 표시 페이지 처리
		//--------------------------------------------------------------------------------------------
		if(isNextPage){
			sb.append("<a class=\"pageBtn _next\" href='"+pageUrl+"&page=");
			sb.append(endPage + 1);
			sb.append("'>다음 페이지로 이동</a>"); // 클릭이 되는 화살표
		} else {
			sb.append("<a class=\"pageBtn _last\" href=\"#none\">다음 페이지로 이동</a>"); // 클릭이 안되는 화살표
		}
		//
		return sb.toString();
	}

	// 페이지 URL 생성
	public String setPageUrl(HashMap<String, Object> paramMap) throws Exception {
		//
	    StringBuilder sb = new StringBuilder();
	    sb.append(paramMap.get("pageUrl")).append("?");
	    //
	    for(String key : paramMap.keySet()) {
	        // 페이징 계산값 제외
	        if("page".equals(key) || "nowPage".equals(key) || "sIdx".equals(key) ||
	        		"eIdx".equals(key) || "listCnt".equals(key) || "blockList".equals(key) || "blockPage".equals(key)) {
	            continue;
	        }
	        //
	        Object value = paramMap.get(key);
	        sb.append(key).append("=").append(value == null ? "" : value).append("&");
	    }

	    // 마지막 & 제거
	    if(sb.charAt(sb.length()-1) == '&') sb.deleteCharAt(sb.length()-1);
	    return sb.toString();
	}
	
	// Null 체크
	public boolean isNull(String str) throws Exception {
		//
		boolean res = false;
		//
		try {
			//--------------------------------------------------------------------------------------------
			// Null 체크
			//--------------------------------------------------------------------------------------------
			if(str == null || "".equals(str) ||  "null".equals(str)) res = true;
			
		} catch (Exception e) {
			//
			throw e;
		}
		//
		return res;
	}

	// Null 체크
	public String nvlProc(String str) throws Exception {
		//
		String res = "";
		//
		try {
			//--------------------------------------------------------------------------------------------
			// Null 체크
			//--------------------------------------------------------------------------------------------
			if(str != null && !"".equals(str) &&  !"null".equals(str)) res = str;
			
		} catch (Exception e) {
			throw e;
		}
		//
		return res;
	}
	
	// 암호화
	public String encryptProc(String plainText) throws Exception {
		//
		SecretKey key = null;
		IvParameterSpec ivParameterSpec = null;
		String specName = "";
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 암호화
			//--------------------------------------------------------------------------------------------
			key = AESCrypto.getKey();
			ivParameterSpec = AESCrypto.getIv();
			specName = "AES/CBC/PKCS5Padding";
			
		} catch (Exception e) {
			throw e;
		}
		//
		return AESCrypto.encrypt(specName, key, ivParameterSpec, plainText);
	}
	
	// 복호화
	public String decryptProc(String plainText) throws Exception {
		//
		SecretKey key = null;
		IvParameterSpec ivParameterSpec = null;
		String specName = "";
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 복호화
			//--------------------------------------------------------------------------------------------
			key = AESCrypto.getKey();
			ivParameterSpec = AESCrypto.getIv();
			specName = "AES/CBC/PKCS5Padding";
			
		} catch (Exception e) {
			throw e;
		}
		//
		return AESCrypto.decrypt(specName, key, ivParameterSpec, plainText);
	}
}
