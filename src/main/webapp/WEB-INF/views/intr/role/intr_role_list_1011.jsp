<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="jakarta.tags.core"%>
<%@ taglib prefix="fn"      uri="jakarta.tags.functions" %>

<c:if test="${fn:length(defaultList)>0}">
	<ul class="ul_1">
		<c:forEach var="list" items="${defaultList}" varStatus="status">
			<li class="tree_item li_1">
				<span class="tree_icon icon_list"></span>
				<a class="a_btn" id="${list.roleCd}" href="#" onclick="detCall('${list.roleCd}');">${list.roleNm}</a>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${fn:length(defaultList) == 0}">
	<ul class="ul_1">
		<li class="tree_item li_1">
			등록된 권한이 없습니다.
		</li>
	</ul>
</c:if>
