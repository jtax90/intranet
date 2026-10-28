<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<ul id="fileUl">
	<c:forEach var="list" items="${fileList}" varStatus="status">
		<li id="fileLi${status.index}">
			<input type="hidden" id="fileId${status.index}" name="none${status.index}" value="${list.fileSno}">
			<img src="resources/images/icon/icon_file.png" width="20" height="20" >
			<a href="javascript:void(0);" onclick="fileDel('delete',${status.index});"><span>${list.fileNm}</span></a>
		</li>
	</c:forEach>
</ul>    