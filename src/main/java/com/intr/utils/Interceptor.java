package com.intr.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.intr.svc.LogService;
import com.intr.vo.EmpVO;

public class Interceptor implements HandlerInterceptor {
	//
	@Autowired
	LogService logService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 요청 URI
		String requestURI = request.getRequestURI().substring(request.getContextPath().length());

		//  세션 체크 제외
		if (isExcludeUri(requestURI)) {
			// Controller 동작
	        return true; 
        }
		
		// 세션 체크
		HttpSession session = request.getSession();
		EmpVO loginInfo = (EmpVO)session.getAttribute("empVO");
		
		if (loginInfo == null) {
            session.setAttribute("alertMsg", "세션 정보가 존재하지 않습니다.");
            response.sendRedirect(request.getContextPath() + "/intrMainInqy1010.do");
            return false;
        }
		
        // Controller 동작
        return true;
    }
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //
    }
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		//
		try {
			//
			String requestUri = request.getRequestURI().substring(request.getContextPath().length());
			if (isExcludeUri(requestUri) || exception == null) {
				return;
			}

			// 세션 확인
			HttpSession session = request.getSession(false);
			EmpVO loginInfo = session == null ? null : (EmpVO) session.getAttribute("empVO");

			// 처리 내용
			String logMsg = "정상 처리";
			String logCont = request.getMethod() + " " + requestUri;
			if (exception != null) {
				StringWriter writer = new StringWriter();
				exception.printStackTrace(new PrintWriter(writer));
				logMsg = exception.toString();
				logCont = writer.toString();
			}

			// 로그 정보
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("logMsg", logMsg);
			paramMap.put("logCont", logCont);
			paramMap.put("requestUri", requestUri);
			paramMap.put("ipAddress", request.getRemoteAddr());
			paramMap.put("regIdx", loginInfo == null ? null : loginInfo.getEmpIdx());

			// 로그 등록
			logService.logProcService1010(null, paramMap);
	
		} catch (Exception e) {
			logger.debug("Exception : 접속 로그 저장 중 에러가 발생했습니다. (" + e.getMessage() + ")");
	    }
    }
	
	//  세션 체크 제외
	private boolean isExcludeUri(String requestURI) {
	    return requestURI.equals("/")
	        || requestURI.equals("/intrMainInqy1010.do")
	        || requestURI.equals("/intrMainProc1010.do")
	        || requestURI.equals("/intrPopupInqy1021.do")
	        || requestURI.equals("/intrPopupInqy1022.do")
	        || requestURI.equals("/sendMail.do")
	        || requestURI.equals("/intrPopupInqy1023.do")
	        || requestURI.equals("/intrEmpProc1050.do")
	        || requestURI.startsWith("/resources/")
	        || requestURI.startsWith("/error/")
	        || requestURI.startsWith("/ckeditor/");
	}
}
