<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" 		uri="jakarta.tags.core" %>
<%@ taglib prefix="fn"      uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" 	uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>

<!-- 휴가 조회 -->
<div class="post_view mg0 mt20 form_panel leave_form">
    <dl class="form_row">
        <dt class="form_label">휴가자</dt>
        <dd class="form_value">${defaultInfo.orgNm} ${defaultInfo.empNm} ${defaultInfo.rankNm}</dd>
        <dt class="form_label">휴가 유형</dt>
        <dd class="form_value">${defaultInfo.leavtypeNm}</dd>
	</dl>
    <dl class="post_info form_row">
        <dt class="form_label">휴가 기간</dt>
        <dd class="form_value">
			<span class="date">
				<fmt:parseDate value="${defaultInfo.leavSdt}" var="parseSdt" pattern="yyyyMMdd"/>
				<fmt:formatDate value="${parseSdt}" var="formatSdt" pattern="yyyy-MM-dd"/>
				
				<fmt:parseDate value="${defaultInfo.leavEdt}" var="parseEdt" pattern="yyyyMMdd"/>
				<fmt:formatDate value="${parseEdt}" var="formatEdt" pattern="yyyy-MM-dd"/>
				${formatSdt} ~ ${formatEdt}
			</span>
		</dd>
	</dl>
    <dl class="form_row">
        <dt class="form_label">적요</dt>
        <dd class="post_text form_value form_content">
			<pre>${defaultInfo.leavRsn}</pre>
		</dd>
	</dl>
</div><!-- End post_view -->
