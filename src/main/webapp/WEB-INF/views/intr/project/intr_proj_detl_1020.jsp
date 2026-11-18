<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1010.jsp" %>

<head>
	<script type="text/javascript">
		// 담당자 선택
		function popCall(){
			try {
				//
				var obj = new Object();
				//
				obj["mappingId"] = "intrPopupInqy1011.do";
				obj["areaType"] = "emp";
				obj["width"] = "550"
				obj["height"] = "420";
				//		
				ajaxPopup(obj);
				
			} catch (error) {
		        console.error("[Error] 담당자 선택 : ", error.message);
			}
		}
	
		// 프로젝트 등록 처리
		function regProc(f){
			try {
	  			// 유효성 검증
				if(!requiredProc()){return;};
				//
				var projSdt = $("input[name=projSdt]").val().replaceAll("-","")
				var projEdt = $("input[name=projEdt]").val().replaceAll("-","");
				var status = $("#statList").text();
				
				// 계약기간
				if(projSdt == ''){
					alert("<spring:message code="PROJ.SDT.NONE"/>");
					$("#projSdt").focus();
					return;
				}
				if(projEdt == ''){
					alert("<spring:message code="PROJ.EDT.NONE"/>");
					$("#projEdt").focus();
					return;
				}
				if(projSdt > projEdt){
					alert("<spring:message code="PROJ.DT.PAST"/>");
					$("#projSdt").focus();
					return;
				}
				// 진행상태
				if(status == ''){
					alert("<spring:message code="PROJ.STAT.NONE"/>");
					return;
				}
				//
				if(confirm("등록하시겠습니까?")){
					var param = $("#form").serialize();
					$.ajax({
						type: 'post',
						url: 'intrProjProc1010.do',
						data: param,
						dataType: 'html',
						success: function(data){
							var json = eval(data);
							if(json[0].res == 'YES'){
								alert("<spring:message code="PROC.SUCCESS"/>");
								location.href = "intrProjInqy1010.do?pageUrl=Proj";
							}else if(json[0].res == 'NO'){
								alert("<spring:message code="PROC.FAIL"/>");
								return;
							}else{
								alert("<spring:message code="PROC.EXISTS"/>");
								return;
							}
						},
						error: function(res, status, error){
							alert("<spring:message code="PROC.ERROR"/>");
						}
					});
				}
				
			} catch (error) {
		        console.error("[Error] 프로젝트 등록 처리 : ", error.message);
			}
		}
		
		// 목록으로
		function listCall() {
			try {
				//
				formSubmit('intrProjInqy1010.do');
				
			} catch (error) {
		        console.error("[Error] 목록으로 : ", error.message);
			}
		}
	</script>
</head>

<body id="main">
<form id="form" method="POST">
	<!-- 메뉴 -->
	<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1030.jsp" %>
	
	<!-- 담당자 팝업 -->
 	<div id="empArea" class="popupArea hide">
		<c:import url="/WEB-INF/views/intr/comm/popup/intr_popup_inqy_1010.jsp"></c:import>	
	</div>
	
	<div class="main_wrap">
		<!-- 좌측 메뉴 -->
		<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1031.jsp" %>

		<div class="content_wrap">
			<div class="content_area">
			<article class="sub_article">
				<div class="content">
					<div class="sub_content">					
                        <div class="form_area">
							<input type="hidden" id="page" name="page" value="${param.page}">
							<input type="hidden" id="pageUrl" name="pageUrl" value="${param.pageUrl}">
							<input type="hidden" id="srchNm" name="srchNm" value="${param.srchNm}">
							<input type="hidden" id="srchSdt" name="srchSdt" value="${param.srchSdt}">
							<input type="hidden" id="srchEdt" name="srchEdt" value="${param.srchEdt}">
							<input type="hidden" id="orgNm" name="orgNm" value="${param.orgNm}">
							<input type="hidden" id="rankNm" name="rankNm" value="${param.rankNm}">
							<input type="hidden" id="srchStatNm" name="srchStatNm" value="${param.srchStatNm}">
							<input type="hidden" id="srchStatCd" name="srchStatCd" value="${param.srchStatCd}">					
							<input type="hidden" id="srchIdx" name="srchIdx" value="${param.srchIdx}">
							<input type="hidden" id="empPcd" name="ownerIdx" value="">
								
							<div class="post_wrap">
                                <h2 class="content_heading">프로젝트 등록</h2>
                                <div class="post_view form_panel form_document">
                                    <dl class="form_row">
                                        <dt class="form_label">
											<label for="post-title">&#10003; 프로젝트명</label>
										</dt>
                                        <dd class="form_value">
                                            <input class="form_input" type="text" id="projTitle" title="프로젝트명" name="projTitle" isRequired="Y">
										</dd>
                                        <dt class="form_label">&#10003; 담당자</dt>
                                        <dd class="form_value form_picker">
                                            <input type="button" class="btn_blue vertical_top btn_action" value="선택" onclick="popCall();">
                                            <input type="text" id="empPnm" class="form_input field_input" title="담당자" disabled="disabled" isRequired="Y">
										</dd>
									</dl>
                                    <dl class="form_row">
                                        <dt class="form_label">&#10003; 계약기간</dt>
                                        <dd class="form_value">
                                            <input type="text" class="srch_cdt_date srchSdt form_input" id="projSdt" name="projSdt" title="계약기간 (시작)" readonly="readonly" isRequired="Y" />
												~
                                            <input type="text" class="srch_cdt_date srchEdt form_input" id="projEdt" name="projEdt" title="계약기간 (종료)" readonly="readonly" isRequired="Y" />
										</dd>
                                        <dt class="form_label">&#10003; 진행상태</dt>
                                        <dd class="form_value form_pair">
                                        	<div class="select_wrap">
												<div id="statList" class="sList select_box choice_value">${statList[1].codeNm}</div>
												<input type="hidden" id="statusCd" name="statusCd" value="${statList[1].codeCd}">
												<input type="hidden" id="statusNm" name="statusNm" value="${statList[1].codeNm}">
											
												<ul class="sUl select_ul scroll_wrap choice_options">
													<c:forEach var="list" items="${statList}">
														<c:if test="${list.codeCd != null and list.codeCd != ''}">
															<li class="choice_item" setNm="${list.codeNm}" setCd="${list.codeCd}">${list.codeNm}</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
                                        </dd>
									</dl>
                                    <dl class="form_row">
                                        <dt class="form_label"><label for="post_text">&#10003; 개요/설명</label></dt>
                                        <dd class="post_text form_value form_content">
                                            <textarea class="form_textarea" id="projCont" title="개요/설명" name="projCont" isRequired="Y"></textarea>
										</dd>
									</dl>
								</div><!-- End post_write -->
                                <div class="btn_right btn_wrap">
                                    <button type="button" class="btn_navy_thin btn_action action_compact" onclick="regProc(this.form);">등록</button>
                                    <button type="button" class="btn_gray_thin btn_action action_compact" onclick="listCall();">취소</button>
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