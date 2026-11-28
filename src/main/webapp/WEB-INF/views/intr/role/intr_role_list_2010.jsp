<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/views/intr/comm/include/intr_include_1010.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		// 첫번째 권한 클릭
		$(".tree_area").find("ul li:first-child").find('.a_btn').trigger('click');
	});
	
	// 선택 항목 음영 처리
	$(document).on('click','.a_btn',function() {
		$(".a_btn").each(function() {
			$(this).removeClass('list_bg');
		})
		//
		$(this).addClass('list_bg');
	});
	
	// 전체 체크 박스 갱신
	function syncMenuSelection(panel) {
		var menus = panel.find("tbody input[type=checkbox]");
		var selected = menus.filter(":checked").length;
		//
		panel.find(".role_selection_heading input").prop({checked: selected > 0 && selected === menus.length, indeterminate: selected > 0 && selected < menus.length});
	}
	
	// 메뉴 전체 선택·해제
	$(document).on("change", "#menuArea .role_selection_heading input", function() {
		var panel = $(this).closest(".role_members");
		panel.find("tbody input[type=checkbox]").prop("checked", this.checked);
		syncMenuSelection(panel);
	});
	
	// 개별·하위 메뉴 선택 처리
	$(document).on("change", "#menuArea tbody input[type=checkbox]", function() {
		var panel = $(this).closest(".role_members");
		if ($(this).closest(".role_menu_parent").length) {
			var parentByMenu = Object.create(null);
			$("#menuInfo input[name=menuCd]").each(function() {
				parentByMenu[this.value] = this.getAttribute("data-parent-menu");
			});
			//
			var menuCode = this.value;
			var checked = this.checked;
			panel.find("tbody input[type=checkbox]").each(function() {
				var parentCode = parentByMenu[this.value];
				while (parentCode && parentCode !== menuCode) {
					parentCode = parentByMenu[parentCode];
				}
				if (parentCode === menuCode) {
					this.checked = checked;
				}
			});
		}
		// 전체 선택 갱신
		syncMenuSelection(panel);
	});

	// 대메뉴명 클릭 시 체크 전환
	$(document).on("click", "#menuArea .role_menu_parent .role_menu_name", function() {
		$(this).closest("tr").find("input[type=checkbox]").trigger("click");
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
	            	$("#menuArea").css("display","inline-flex");
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
			
			$.ajax({
	    		type : 'post',
	        	url : 'intrRoleInqy2011.do',
	            data : param,
	            dataType : 'html',
	            success : function(data){
	     				//
	            	    $("#roleInfo").html(data);
	            	    $("#menuArea").css("display","inline-flex");
	            },
	            error : function(data){
	            	//
					alert("<spring:message code="PROC.ERROR"/>");
	            }
	      	});
			
		} catch (error) {
	        console.error("[Error] 권한 상세조회 : ", error.message);
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
									<div class="post_wrap role_workspace">
									<input type="hidden" id="roleCd" class="roleCd" name="roleCd" value="">
								
                                    <h2 class="content_heading">메뉴 권한 부여</h2>
									<div class="tab_container">
										<div class="tab_wrapper">
											<ul class="tab_list">
												<li class="tab_item"><a class="tab_link" href="${pageContext.request.contextPath}/intrRoleInqy1010.do">권한 관리</a></li>
												<li class="tab_item active"><a class="tab_link" href="${pageContext.request.contextPath}/intrRoleInqy2010.do" aria-current="page">메뉴 권한 부여</a></li>
												<li class="tab_item"><a class="tab_link" href="${pageContext.request.contextPath}/intrRoleInqy3010.do">사용자 권한 부여</a></li>
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
										
									<div class="tree_wrap tree_workspace role_member_workspace">
										<div id="tree_area" class="scroll_wrap tree_area tree_navigation role_list_panel">
											<h3 class="role_list_heading">권한 목록</h3>
											<div id="roleTree" class="tree">
												<jsp:include page="intr_role_list_1011.jsp"/>
											</div>	
										</div>
											
                                        <div id="menuArea" class="menu_area menu_assignment">
											<!-- 권한 메뉴 목록 -->
											<div id="menuInfo" class="tree_info tree_choices ml20">
												<div class="list_panel list_panel_fill role_members">
													<h3 class="role_list_heading role_selection_heading">
														<label>
															<span class="check_box" title="전체 선택">
																<input type="checkbox" class="checkbox_input menuChk">
																<span class="checkbox_label"><span class="checkbox_mark"></span></span>
															</span>
															전체 메뉴
														</label>
													</h3>
													<div class="scroll_wrap list_body">
														<table class="post_table data_table menuTbl">
															<caption>권한 목록 테이블</caption>
															<colgroup>
																<col class="role_members_selection_col">
																<col>
															</colgroup>
															<tbody>
										                       	<c:forEach var="list" items="${tMenuList}" varStatus="status"> 
																<tr class="${list.lv eq 1 ? 'role_menu_parent' : 'role_menu_child'}">
                                                                    <td class="table_cell">
								                                       <span class="check_box">
                                                                            <input type="checkbox" class="checkbox_input check_box" id="menuCd" name="menuCd" value="${list.menuCd}" menuNm="${list.menuNm}" menutypeCd="${list.menutypeCd}" data-parent-menu="${list.upprMenuCd}" menuLv="${list.lv}">
                                                                            <label class="checkbox_label_lead checkbox_label" for="chk-yes"><span class="checkbox_mark"></span></label>
																		</span>
																	</td>
                                                                    <td class="table_cell role_menu_name">
                                                                        <c:out value="${list.menuNm}"/>
																	</td>
																</tr>
								                                </c:forEach>
															</tbody>
														</table>
													</div>
												</div><!-- End post_wrap -->
											</div>
											
											<!-- 화살표 -->
											<button type="button" id="treeArrow" class="btn_blue_thin btn_action action_compact role_member_add" onclick="moveBtn(this.form);">추가</button>
											
											<!-- 권한 목록 -->
											<div id="roleInfo" class="tree_info tree_detail"></div>
										</div> <!-- End menu_area -->
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
