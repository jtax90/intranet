<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn"      uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="role_detail_panel">
	<h3 class="role_list_heading">권한 상세</h3>
	<div class="mg0 form_panel">
    <dl class="form_row">
        <dt class="form_label">
			<label for="post-title">권한명</label>
		</dt>
        <dd class="form_value">
			${defaultInfo.roleNm} 
		</dd>
	</dl>
    <dl class="form_row">
        <dt class="form_label">
			권한 설명
		</dt>
		<dd class="form_value">
			<c:out value="${defaultInfo.roleDesc}"/>
		</dd>
	</dl>
    <dl class="form_row">
        <dt class="form_label">
			<label for="post-title">사용여부</label>
		</dt>
        <dd class="form_value">
			<c:if test="${defaultInfo.useYn eq 'Y'}">예</c:if>
			<c:if test="${defaultInfo.useYn ne 'Y'}">아니오</c:if>
		</dd>
	</dl>
    <dl class="form_row">
        <dt class="form_label">
			<label for="post-title">비고</label>
		</dt>
        <dd class="form_value">
			<div class="role_remark_display">${defaultInfo.remark}</div>
		</dd>
	</dl>
	</div>
</div>
<div class="role_detail_actions">
	<button type="button" class="btn_navy_thin btn_action action_compact" onclick="modCall(this.form);">수정</button>
	<button type="button" class="btn_gray_thin btn_action action_compact" onclick="delProc(this.form);">삭제</button>
</div>
