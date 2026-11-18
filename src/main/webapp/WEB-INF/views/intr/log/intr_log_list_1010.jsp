<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1010.jsp" %>

<script type="text/javascript">
	// 검색 조회
	function listCall(f){
		try {
			//
			formSubmit("intrLogInqy1010.do");
			
		} catch (error) {
	        console.error("[Error] 검색 조회 : ", error.message);
		}
	}
	
	// 메세지 조회
	function popCall(logId){
		try {
			//
			var obj = new Object();
			obj["mappingId"] = "intrPopupInqy2021.do";
			obj["areaType"] = "log";
			obj["logId"] = logId;
			obj["width"] = "670";
			obj["height"] = "420";
			//
			ajaxPopup(obj);
		
		} catch (error) {
			console.error("[Error] 메세지 조회 : ", error.message);
		}
	}
</script>
<body id="main">
<form id="form" name="form" method="POST">
	<!-- 메뉴 -->
	<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1030.jsp" %>
	
	<!-- 메세지 조회 팝업 -->
 	<div id="logArea" class="popupArea hide">
		<c:import url="/WEB-INF/views/intr/comm/popup/intr_popup_inqy_2020.jsp"></c:import>	
	</div>
	
	<div class="main_wrap">
		<!-- 좌측 메뉴 -->
		<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1031.jsp" %>
		
		<div class="content_wrap">
			<div class="content_area">
				<article class="sub_article">
						<div class="content">
							<div id="sub_content">					
								<div class="form_area">
									<div class="post_wrap">
										<input type="hidden" id="page" name="page" value="${param.page}">
										<input type="hidden" id="pageUrl" name="pageUrl" value="${param.pageUrl}">
			
                                        <h2 class="content_heading">로그 관리</h2>
										<div class="srch_wrap">
											<div class="right_srch_area">
												<!-- 요청일자 -->
												<div class="srch_area">
													<label class="srch_label">요청일자</label>
													<input type="text" class="srch_cdt_date srchSdt" id="srchSdt" name="srchSdt" value="${param.srchSdt}" readonly="readonly" />
													~
													<input type="text" class="srch_cdt_date srchEdt" id="srchEdt" name="srchEdt" value="${param.srchEdt}" readonly="readonly"/>
												</div>
												
												<!-- 요청자 -->
												<div class="float_right">
													<div class="srch_area">
														<label class="srch_label">요청자</label>
														<input type="text" id="srchNm" name="srchNm" class="srch_cdt_text" value="${param.srchNm}" onkeydown="enterListCall(this.form);">
													
                                                        <input type="button" class="btn_blue btn_action" value="조회" onclick="listCall(this.form);">
                                                        <input type="button" class="btn_gray btn_action" value="초기화" onclick="initCall();">
													</div>
			                                	</div>
											</div>
										</div>
										
										<div class="post_table_wrap">
											<table class="post_table data_table">
												<caption>로그 목록 조회</caption>
												<colgroup>
													<col class="w10per">
													<col class="w20per">
													<col class="w15per">
													<col class="w15per">
													<col class="w15per">
													<col class="w15per">
												</colgroup>
												<thead>
													<tr>
														<th class="table_header" scope="col">메시지</th>
														<th class="table_header" scope="col">요청 URI</th>
														<th class="table_header" scope="col">IP 주소</th>
														<th class="table_header" scope="col">부서</th>
														<th class="table_header" scope="col">요청자</th>
														<th class="table_header" scope="col">요청일자</th>
													</tr>
												</thead>
												<tbody>
			                                       <c:forEach var="list" items="${defaultList}" varStatus="status"> 
													<tr>
														<td class="table_cell">
															<a class="show_view a_title" onclick="popCall('${list.logId}');"><c:out value="${list.logMsg}"/></a>
														</td>
														<td class="table_cell"><c:out value="${list.requestUri}"/></td>
														<td class="table_cell"><c:out value="${list.ipAddress}"/></td>
														<td class="table_cell"><c:out value="${list.orgNm}"/></td>
														<td class="table_cell"><c:out value="${list.empNm}"/></td>
														<td class="table_cell"><span class="date">${list.regDt}</span></td>
													</tr>
			                                        </c:forEach>
			                                        
			                                        <!-- 글이 없는 경우 -->
			                                        <c:if test="${empty defaultList}">
			                                            <tr>
                                                            <td class="table_cell" align="center" colspan="6">
			                                              	      등록된 이력이 없습니다.
			                                                </td>
			                                            </tr>
			                                        </c:if>
												</tbody>
											</table>
											
											<c:if test="${not empty defaultList}">
												<div class="paging_area">
													<div class="list_cnt">총 건수 : ${defaultList[0].listCnt}건</div>
													<ul class="paging">
														<li class="">${pageMenu}</li>
													</ul>
												</div><!-- End paging_wrap -->
											</c:if>
										</div>
									</div><!-- End post_wrap -->
								</div><!-- End form_area -->
							</div><!-- End sub_content -->
						</div><!-- End content -->
				</article>
			</div>
		</div>
	</div>
</form>
</body>

<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1020.jsp" %>
