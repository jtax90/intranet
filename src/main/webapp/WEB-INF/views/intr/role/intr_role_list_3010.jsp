<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1010.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		// 첫번째 권한 클릭
		$("#roleTree").find("ul li:first-child").find('.a_btn').trigger('click');
	});
	
	$(document).on('click','.a_btn',function() {
		$(".a_btn").each(function() {
			$(this).removeClass('list_bg');
		});
		//
		$(this).addClass('list_bg');
	});	
	
	// 부서 하위 항목 펼침 및 접기
	$(document).ready(function() {
		$("#empInfo").on("click", "button.role_employee_department", function() {
			this.setAttribute("aria-expanded", String(this.getAttribute("aria-expanded") !== "true"));
			//
			var collapsedLevel = Infinity;
			$("#empInfo .role_employee_item").each(function() {
				var level = Number(this.dataset.level);
				this.hidden = level > collapsedLevel;
				if (!this.hidden) {
					collapsedLevel = $(this).find('[aria-expanded="false"]').length ? level : Infinity;
				}
			});
		});
	});

	// 패널별 전체 선택 상태
	function syncRoleSelection(panel) {
		var users = panel.find("input.empIdx, input[id=roleEmpIdx]");
		var selected = users.filter(":checked").length;
		//
		panel.find(".role_selection_heading input").prop({checked: selected > 0 && selected === users.length, indeterminate: selected > 0 && selected < users.length});					
	}

	$(document).on("change", "#empArea .role_selection_heading input", function() {
		var panel = $(this).closest("#empInfo, .role_members");
		panel.find("input.empIdx, input[id=roleEmpIdx]").prop("checked", this.checked);
		//
		syncRoleSelection(panel);
	});

	$(document).on("change", "#empArea input.empIdx, #empArea input[id=roleEmpIdx]", function() {
		syncRoleSelection($(this).closest("#empInfo, .role_members"));
	});
		
	// 권한 조회
	function listCall(f){
		try {
			//
			var param = $("#form").serialize();
			
			$.ajax({
	    		type : 'post',
	        	url : 'intrRoleInqy1011.do',
	            data : param,
	            dataType : 'html',
	            success : function(data){
	            	//
	            	$("#roleTree").html(data);
	            	$("#empArea").css("display","none");
	            },
	            error : function(data){
	            	//
					alert("<spring:message code="PROC.ERROR"/>");
	            }
	      	});
			
		} catch (error) {
	        console.error("[Error] 권한 조회 : ", error.message);
		}
	}

	// 권한 상세보기
	function detCall(roleCd){
		try {
			// 권한 코드 지정
			$(".roleCd").val(roleCd);
			var param = $("#form").serialize();
			//
			$.ajax({
	    		type : 'post',
	        	url : 'intrRoleInqy3011.do',
	            data : param,
	            dataType : 'html',
	            success : function(data){
	     				//
	            	    $("#empTree").html(data);
	            	    $("#empArea").css("display","inline-flex");
	            },
	            error : function(data){
	            	//
					alert("<spring:message code="PROC.ERROR"/>");
	            }
	      	});
			
		} catch (error) {
	        console.error("[Error] 권한 상세보기 : ", error.message);
		}
	}
</script>
<body id="main">
<form id="form" onsubmit="return false;">
	<!-- 메뉴 -->
	<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1030.jsp" %>
	
	<div class="main_wrap">
		<!-- 좌측 메뉴 -->
		<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1031.jsp" %>
		
		<div class="content_wrap">
			<div class="content_area"> 
				<article class="sub_article">
					<div class="content">
						<div id="sub_content">					
							<div class="form_area">
								<input type="hidden" id="roleCd" class="roleCd" name="roleCd" value="">
							
								<div class="post_wrap role_workspace">
                                    <h2 class="content_heading">사용자 권한 부여</h2>
									<div class="tab_container">
										<div class="tab_wrapper">
											<ul class="tab_list">
												<li class="tab_item"><a class="tab_link" href="${pageContext.request.contextPath}/intrRoleInqy1010.do">권한 관리</a></li>
												<li class="tab_item"><a class="tab_link" href="${pageContext.request.contextPath}/intrRoleInqy2010.do">메뉴 권한 부여</a></li>
												<li class="tab_item active"><a class="tab_link" href="${pageContext.request.contextPath}/intrRoleInqy3010.do" aria-current="page">사용자 권한 부여</a></li>
											</ul>
										</div>
									</div>
		                                <div class="srch_wrap">
											<div class="right_srch_area">
												<!-- 제목 -->
												<div class="srch_area">
													<label class="srch_label">제목</label>		
													<input type="text" id="srchNm" name="srchNm" class="srch_cdt_text" value="${param.srchNm}" onkeydown="enterListCall(this.form);">
												
                                                    <input type="button" class="btn_blue btn_action" value="조회" onclick="listCall(this.form);">
                                                    <input type="button" class="btn_gray btn_action" value="초기화" onclick="initCall();">
												</div>
		                                	</div>
		                                </div>
		                                <!-- end Form srch_wrap  -->
										
										<div class="tree_wrap tree_workspace role_member_workspace">
											<div id="treeArea" class="scroll_wrap tree_area tree_navigation role_list_panel">
												<h3 class="role_list_heading">권한 목록</h3>
												<div id="roleTree" class="tree">
													<jsp:include page="intr_role_list_1011.jsp"/>
												</div>	
											</div>
											
                                            <div id="empArea" class="emp_area member_assignment">
												<!-- 사용자 목록 -->
												<div id="empInfo" class="scroll_wrap tree_area tree_navigation role_list_panel ml20">
													<h3 class="role_list_heading role_selection_heading">
														<label>
															<span class="check_box" title="전체 선택">
																<input type="checkbox" id="totalIdx" name="totalIdx" class="checkbox_input checkbox totalIdx">
																<span class="checkbox_label"><span class="checkbox_mark"></span></span>
															</span>
															사용자 목록
														</label>
													</h3>
													<ul class="role_employee_tree">
														<c:forEach var="list" items="${empList}" varStatus="status">
															<li class="role_employee_item" data-level="${list.lv}" style="--employee-level: ${list.lv}">
																<c:choose>
																	<c:when test="${list.isleaf eq 'Y'}">
																		<label class="role_employee_row">
																			<span class="check_box">
																				<input type="checkbox" id="empIdx${status.index}" class="checkbox_input empIdx" name="empIdx" value="${list.empIdx}" empNm="<c:out value="${list.empNm}"/>" orgNm="<c:out value="${list.orgNm}"/>">
																				<span class="checkbox_label"><span class="checkbox_mark"></span></span>
																			</span>
																			<img src="resources/images/icon/icon_file.png" width="15" height="15" alt="">
																			<c:out value="${list.empNm}"/>
																		</label>
																	</c:when>
																	<c:when test="${empList[status.index + 1].lv gt list.lv}">
																		<button type="button" class="role_employee_department" aria-expanded="true">
																			<img src="resources/images/icon/icon_folder.png" width="15" height="15" alt="">
																			<c:out value="${list.orgNm}"/>
																		</button>
																	</c:when>
																	<c:otherwise>
																		<span class="role_employee_department">
																			<img src="resources/images/icon/icon_folder.png" width="15" height="15" alt="">
																			<c:out value="${list.orgNm}"/>
																		</span>
																	</c:otherwise>
																</c:choose>
															</li>
														</c:forEach>
													</ul>
												</div>

												<!-- 화살표 -->
												<button type="button" id="treeArrow" class="btn_blue_thin btn_action action_compact role_member_add" onclick="moveBtn(this.form);">추가</button>
												
												<!-- 사용자 트리 -->
												<div id="empTree" class="tree_info tree_detail"></div>
											</div><!-- End emp_area -->
										</div><!-- End tree_wrap -->
								</div><!-- End post_wrap -->
							</div><!-- End content_area form_area -->
						</div><!-- End sub_content -->
					</div><!-- End content -->
				</article>
			</div>
		</div>
	</div>
</form>
</body>
	
<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1020.jsp" %>
