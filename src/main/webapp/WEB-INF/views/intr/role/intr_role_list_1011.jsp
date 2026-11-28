<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="jakarta.tags.core"%>

<c:if test="${not empty defaultList}">
	<ul class="ul_1">
		<c:forEach var="list" items="${defaultList}" varStatus="status">
			<li class="role_card_item">
				<a class="a_btn role_card" id="${list.roleCd}" href="#" onclick="detCall(this.id); return false;">
					<span class="role_card_summary">
						<strong class="role_card_name"><c:out value="${list.roleNm}"/></strong>
						<c:if test="${not empty list.roleDesc}">
							<span class="role_card_description"><c:out value="${list.roleDesc}"/></span>
						</c:if>
					</span>
					<span class="role_card_status ${list.useYn eq 'Y' ? 'role_card_status_enabled' : ''}">${list.useYn eq 'Y' ? '사용' : '미사용'}</span>
				</a>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty defaultList}">
	<ul class="ul_1">
		<li class="tree_item li_1">
			등록된 권한이 없습니다.
		</li>
	</ul>
</c:if>
