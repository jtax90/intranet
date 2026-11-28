<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	// 목록 이동
	function moveBtn(f) {
		try {
			// 체크한 건이 없는 경우
			if($("input[id=menuCd]:checked").length==0){
				alert("<spring:message code="CHECK.NONE"/>");
				return;
			}
			
			// 체크한 값 이동
			$("input[id=menuCd]:checked").each(function() {
				// 중복 여부 값
				var dupeYn = 'N';
				// 메뉴 관련 변수
				var menuCd = $(this).val();
				var menuNm = $(this).attr("menuNm");
				var menutypeCd = $(this).attr("menutypeCd");
				var menuLv = $(this).attr("menuLv");
				
				// 메뉴 권한 목록 값
				$("input[id=roleMenuCd]").each(function() {
					// 중복되는 경우 중복 여부 값 변경
					var roleMenuCd = $(this).val();
					//
					if(roleMenuCd==menuCd){
						dupeYn = "Y";
						return; 
					}
				});
				
				// 중복되지 않는 경우
				if(dupeYn=='N'){
					//
					var str = "";
					//
					str += "<tr>"
                    str += "	<td class='table_cell'>";
					str += "		<span class='check_box'>";
                    str += "		<input type='checkbox' class='checkbox_input checkbox' id='roleMenuCd' value='" + menuCd + "'/>"
                    str += "		<label class='checkbox_label' for='chk_local'><span class='checkbox_mark'></span></label></span>";
					str += "		<input type='hidden' name='roleMenuCd' value='" + menuCd + "'/>"
					str += "	</td>";
					//
					str += "<td class='table_cell role_menu_name'></td>";
					//
					str += "</tr>";
					
					var row = $(str);
					row.addClass(menuLv == 1 ? "role_menu_parent" : "role_menu_child");
					row.find(".role_menu_name").text(menuNm);
					$(".roleMenuTbl tbody").append(row);
				}
				
			});
			$("#menuInfo input[name=menuCd]").prop("checked", false);
			syncMenuSelection($("#menuInfo .role_members"));
			syncMenuSelection($("#roleInfo .role_members"));
			
		} catch (error) {
	        console.error("[Error] 목록 이동 : ", error.message);
		}
	}
	
	// 권한 목록 화면 상 제거
	function delCall(){
		try {
			// 유효성 검증
			if($("input[id=roleMenuCd]:checked").length==0){
				alert("<spring:message code="CHECK.NONE"/>");
				return;
			}
			
			// 화면 상 삭제 처리
			$("input[id=roleMenuCd]:checked").each(function() {
				$(this).parents("tr").remove();
			});
			
			// 권한 목록 체크 초기화
			syncMenuSelection($("#roleInfo .role_members"));
			
		} catch (error) {
	        console.error("[Error] 권한 목록 화면 상 제거 : ", error.message);
		}
	}
	
	// 새로고침
	function setListCall() {
		try {
			//
			$('#roleTree .list_bg').trigger("click");
			
		} catch (error) {
	        console.error("[Error] 새로고침 : ", error.message);
		}
	}
	
	// 권한 저장 처리
	function regProc() {
		//
	    try {
	    	//
	        if (!confirm("저장하시겠습니까?")) {
	            return;
	        }
	    	
			// 변수 저장	
	        var param = [
	            {
	                name: "roleCd",
	                value: $("#roleCd").val()
	            }
	        ];
			//
	        $(".roleMenuTbl input[name='roleMenuCd']").each(function(idx) {
	            param.push({
	                name: "menuCd" + idx,
	                value: $(this).val()
	            });
	        });
			//	
	        $.ajax({
	            type: "post",
	            url: "intrRoleProc2010.do",
	            data: $.param(param),
	            dataType: "html",
	            success: function(data) {
	                var json = eval(data);
	
	                if (json[0].res === "YES") {
	                    alert("<spring:message code="PROC.SUCCESS"/>");
	                    setListCall();
	                } else {
	                    alert("<spring:message code="PROC.FAIL"/>");
	                }
	            },
	            error: function() {
	                alert("<spring:message code="PROC.ERROR"/>");
	            }
	        });
	    } catch (error) {
	        console.error("[Error] 권한 저장 처리 :", error.message);
	    }
	}
</script>
<div class="list_panel list_panel_fill role_members">
	<h3 class="role_list_heading role_selection_heading">
		<label>
			<span class="check_box" title="전체 선택">
				<input type="checkbox" class="checkbox_input roleChk">
				<span class="checkbox_label"><span class="checkbox_mark"></span></span>
			</span>
			메뉴 부여
		</label>
	</h3>
	<div class="scroll_wrap list_body">
		<table class="post_table data_table roleMenuTbl">
			<caption>권한 목록 테이블</caption>
			<colgroup>
				<col class="role_members_selection_col">
				<col>
			</colgroup>
			<tbody>
				<c:forEach var="list" items="${roleMenuList}" varStatus="status">
					<tr class="${list.lv eq 1 ? 'role_menu_parent' : 'role_menu_child'}">
                        <td class="table_cell">
							<span class="check_box"> 
                            <input type="checkbox" class="checkbox_input check_box" id="roleMenuCd" value="${list.menuCd}">
                            <label class="checkbox_label_lead checkbox_label" for="chk-yes"><span class="checkbox_mark"></span></label></span>
							<input type="hidden" name="roleMenuCd" value="${list.menuCd}">
						</td>
                        <td class="table_cell role_menu_name">
                            <c:out value="${list.menuNm}"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="role_members_actions">
	<button type="button" class="btn_gray_thin btn_action action_compact" onclick="delCall();">선택 제거</button>
	<button type="button" class="btn_blue_thin btn_action action_compact" onclick="setListCall();">새로고침</button>
	<button type="button" class="btn_navy_thin btn_action action_compact" onclick="regProc(this.form);">저장</button>
</div>
