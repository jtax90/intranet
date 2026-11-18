<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- 로그 메세지 조회 -->
<div class="post_view mg0 mt20 form_panel resignation_form">
	<dl class="form_row">
		<dt class="form_label">요청자</dt>
		<dd class="form_value"><c:out value="${defaultInfo.orgNm}"/> <c:out value="${defaultInfo.empNm}"/></dd>
		<dt class="form_label">요청일자</dt>
		<dd class="form_value"><span class="date">${defaultInfo.regDt}</span></dd>
	</dl>
	<dl class="post_info form_row">
		<dt class="form_label">요청 URI</dt>
		<dd class="form_value"><c:out value="${defaultInfo.requestUri}"/></dd>
		<dt class="form_label">요청자 IP</dt>
		<dd class="form_value"><c:out value="${defaultInfo.ipAddress}"/></dd>
	</dl>
	<dl class="form_row">
		<dt class="form_label">메시지</dt>
		<dd class="post_text form_value form_content">
			<textarea class="form_textarea" readonly="readonly"><c:out value="${defaultInfo.logMsg}"/></textarea>
		</dd>
	</dl>
	<dl class="form_row">
		<dt class="form_label">상세 내용</dt>
		<dd class="post_text form_value form_content">
			<textarea class="form_textarea" readonly="readonly"><c:out value="${defaultInfo.logCont}"/></textarea>
		</dd>
	</dl>
</div><!-- End post_view -->