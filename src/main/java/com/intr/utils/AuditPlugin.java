package com.intr.utils;

import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.intr.vo.EmpVO;

import jakarta.servlet.http.HttpSession;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    	// 동작 SQL 정보 조회
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];

        // SELECT, DELETE는 제외
        SqlCommandType commandType = statement.getSqlCommandType();
        if (commandType != SqlCommandType.INSERT && commandType != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        //
        Object parameter = invocation.getArgs()[1];

        // 현재 프로젝트의 Map 파라미터 대상
        if (!(parameter instanceof Map<?, ?>)) {
            return invocation.proceed();
        }

        // 배치 등 웹 요청이 아닌 경우
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return invocation.proceed();
        }

        // 세션 조회
        HttpSession session = attributes.getRequest().getSession(false);
        EmpVO loginInfo = session == null ? null : (EmpVO) session.getAttribute("empVO");

        // 비로그인 작업은 별도 정책으로 처리
        if (loginInfo == null) {
            return invocation.proceed();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = (Map<String, Object>) parameter;

        // 등록자, 수정자 저장
        paramMap.put("regIdx", loginInfo.getEmpIdx());
        paramMap.put("modIdx", loginInfo.getEmpIdx());

        return invocation.proceed();
    }
}