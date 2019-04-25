<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sw" uri="/WEB-INF/solwind.tld"%>
<style type="text/css">
	ul{
	  list-style-type:none;
	}
</style>
<tags:template>
	<jsp:body>
	 <div class="content-wrapper">
		<div id="alert_danger" class="callout callout-danger" style="display: none;"></div>
	  	<!-- 内容头部 -->
	    <section class="content-header">
	      <h1>
	        角色管理
	        <small>角色信息添加</small>
	      </h1>
	    </section>
	    <!-- 内容头部 /-->
		<!-- 正文区域 -->
		
			<section class="content">
			<!-- 上方提示框 -->
			    <div class="box box-info">
			        <div class="box-header with-border">
			            <ul>
			                <li>
			                    <span style="color:red">*</span>为必填选项
			                </li>
			            </ul>
			        </div>
			        <div class="box-body">
			            <form action="addRoleInfo.do" method="post" onsubmit="return epJudge();" id="role_form" name="role_form" class="form-horizontal bv-form" novalidate="novalidate">
			            	<!-- 隐藏域 -->
			            	<input type="hidden" id="code_search" name="code_search" value="${code_search}">
			            	<input type="hidden" id="name_search" name="name_search" value="${name_search}">
			            	<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}">
			            	<input type="hidden" id="funcs" name="funcs" value="${funcs}">
			            	<!-- 隐藏域 -->
			                <div class="box-body">
			                    <div class="row">
			                        <div class="col-md-6">
			                             <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">角色编码<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <input type="text" placeholder="请输入角色编码" class="form-control" id="code" name="code" data-bv-field="code" value="${sr.code }" maxlength="20">
			                                </div>
			                            </div>
			                            <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">角色名称<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <input type="text" placeholder="请输入角色名称" class="form-control" id="name" name="name" data-bv-field="name" value="${sr.name }">
			                                </div>
			                            </div>
			                            <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">状态<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <tags:select selectId="status" selectName="status" typeCode="COMMON_VALID"  selectValue="1" selectStyle="width: 100%;height: 34px;padding: 6px 12px;border: 1px solid #d2d6de;"></tags:select>
			                                </div>
			                            </div>
			                        </div>
			                        
			                        <!--  菜单   -->
			                        <div id="roleMenuDiv" class="col-md-6" style="font-size: 15px;padding-left: 120px;">
			                         <ul>	
			                         
			                         <li>
			                           <i class="fa fa-stop btn-box-tool"></i>
			                           <input type="checkbox" id="check_all" style="margin:0px !important;vertical-align: middle;">
			                           <a href="#">全部</a>
			                         </li>
										
										<c:forEach items="${roleList }" var="menu" varStatus="status">
										  
											<!-- 一级菜单不可展开  -->
											<c:if test="${empty menu.parentId and empty menu.childList}">
												<li>
													<i class="fa fa-stop btn-box-tool"></i>
													<input class="functions${menu.id}" type="checkbox" name="checkbox" value="${menu.id}" style="margin:0px !important;vertical-align: middle;">
													<a href="#"> ${menu.name }</a>
												</li>
											</c:if>
											<!-- /.一级菜单不可展开  -->
										  
										    <!-- 一级菜单可展开  -->
										    <c:if test="${empty menu.parentId and not empty menu.childList}">
										        
									            <li id="myChecked${menu.id}" ><i class="fa fa-plus-square btn-box-tool ${menu.id}" onclick="secondMenu('${menu.id}')"></i>
									            <input class="functions${menu.id}" type="checkbox" name="checkbox" id="myChecks${menu.id}" onclick="checkChild('${menu.id}')" value="${menu.id}" style="margin:0px !important;vertical-align: middle;">
									            <a href="#"> ${menu.name }</a>
									            <!-- 如果一级菜单的childList不为空就往下继续遍历 -->
									            
									            <div id="secondMenu_div${menu.id}" style="display: none;">
									             <ul>
									            	<!-- 遍历第二层菜单 -->
									                <c:forEach items="${menu.childList}" var="secondChild" varStatus="status">
									                
														<!-- chidList为空的则没有下级菜单，为不可展开第二层菜单  -->
									                    <c:if test="${empty secondChild.childList}">
									                        <li>
									                           <i class="fa fa-stop btn-box-tool"></i>
									                           <input class="functions${menu.id}" type="checkbox" name="checkbox" value="${secondChild.id}" style="margin:0px !important;vertical-align: middle;">
									                           <a href="#">${secondChild.name }</a>
									                        </li>
									                    </c:if>
									                    <!-- /.chidList为空的则没有下级菜单，为不可展开第二层菜单  -->
									                    
									                    <!-- childList不为空，表示还有下级菜单，可展开第二层菜单，此处根据情况只考虑到第三层  -->
									                    <c:if test="${not empty secondChild.childList}">
									                        <li id="myChecked${secondChild.id}">
									                        
								                                 <i class="fa fa-plus-square btn-box-tool ${secondChild.id}" onclick="thirdMenu('${secondChild.id}')"></i>
								                                 <input class="functions${menu.id}" type="checkbox" name="checkbox" onclick="checkChild('${secondChild.id}')" id="myChecks${secondChild.id}" value="${secondChild.id}" style="margin:0px !important;vertical-align: middle;">
								                                 <a href="#">${secondChild.name}</a>
								                                 
								                                 <div id="thirdMenu_div${secondChild.id}" style="display: none;">
								                                 <ul>
							                                        <c:forEach items="${secondChild.childList}" var="thirdChild" varStatus="status">
								                                        <li>
								                                           <!-- <i class="fa fa-stop btn-box-tool"></i> -->
								                                           <input class="functions${menu.id}" type="checkbox" name="checkbox" value="${thirdChild.id}" style="margin:0px !important;vertical-align: middle;">
								                                           <a href="#">${thirdChild.name }</a>
								                                        </li>
								                                    </c:forEach>
								                                 </ul>
								                                 </div>
									                        </li>
									                    </c:if>
									                    <!-- childList不为空，表示还有下级菜单，可展开第二层菜单，此处根据情况只考虑到第三层  -->
									                    
									                </c:forEach>
									                <!-- /.遍历第二层菜单 -->
									             </ul>
									             </div>
										        </li>     
										    </c:if>
										    <!-- /.一级菜单可展开  -->
										    
										</c:forEach>
									  </ul>	
			                        </div>
			                        <!--  /.菜单   -->
			                        
			                    </div>
			                </div>
			                <input type="hidden" name="id" value="">
			                <div class="box-footer text-right">
			                    <button id="back_role" type="button" class="btn btn-default" data-btn-type="cancel"><i class="fa fa-reply">&nbsp;返回</i></button>
			                    <button id="myfs" type="submit" class="btn btn-primary" data-btn-type="save"><i class="fa fa-save">&nbsp;提交</i></button>
			                </div>
			            </form>
			            <!-- 隐藏form  -->
			            <form id="back_form" action="goBackRole.do" method="post">
			            	<input type="hidden" name="code" value="${code_search}">
			            	<input type="hidden" name="name" value="${name_search}">
			            	<input type="hidden" name="pageNum" value="${pageNum}">
			            </form>
			            <!-- /隐藏form  -->
			        </div>
			    </div>
			</section>
	    <!-- 正文区域 /-->
	  </div>
	</jsp:body>
</tags:template>
<script type="text/javascript">
//form js校验
function epJudge(){
	
	var code = $("#code").val().trim();
	var name = $("#name").val().trim();
	
	//必填项判断
	if(name==""||code==""){
		return false;
	}
	getMenuId();
	return true;
}

//折叠二级菜单
function secondMenu(id){
	var menu = $("."+id);
	if(menu.hasClass("fa-minus-square")){
		$("#secondMenu_div"+id).css("display","none");
		menu.addClass("fa-plus-square");
		menu.removeClass("fa-minus-square");
	}else{
		$("#secondMenu_div"+id).css("display","block");
		menu.addClass("fa-minus-square");
		menu.removeClass("fa-plus-square");
	}
}

//折叠三级菜单
function thirdMenu(id){
	var menu = $("."+id);
	if(menu.hasClass("fa-minus-square")){
		$("#thirdMenu_div"+id).css("display","none");
		menu.addClass("fa-plus-square");
		menu.removeClass("fa-minus-square");
	}else{
		$("#thirdMenu_div"+id).css("display","block");
		menu.addClass("fa-minus-square");
		menu.removeClass("fa-plus--square");
	}
}

//获取div下所有被勾选的input的值,然后将其填进input中，传回后台，后台使用split重新分割成字符串数组
function getMenuId() {
	var str="";
	$("#roleMenuDiv").find("input[name='checkbox']").each(function () {
        if ($(this).is(":checked")) {
            //alert($(this).val());
            str += $(this).val()+",";
        }
    })
    str = str.substring(0,str.length-1);
	$("#funcs").attr("value",str);
}

//用于二级三级菜单全选功能
function checkChild(id){

	if(!$("#myChecks"+id).is(":checked")){
		$("#myChecked"+id).find("input[name='checkbox']").each(function () {
			$(this).prop("checked",false);
		})
	}else{
		$("#myChecked"+id).find("input[name='checkbox']").each(function () {
			$(this).prop("checked",true);
		})
	}
}

$(function(){
	//登录名称重复之后提示框
	if("${respcode}"!=""){
		$("#alert_danger").css('display','block');
		$("#alert_danger").html("${respmsg}");
		setTimeout(function(){$("#alert_danger").css("display","none");$("#alert_danger").html("");}, 2000);
	}
	
	//监听全选按钮的点击事件
	$("#check_all").click(function() {
		//如果全选按钮当前没有被选中，则点击之后选中所有按钮
		if($("#check_all").is(":checked")){
			$("[name='checkbox']").prop("checked",true);//全选
		}else{
			$("[name='checkbox']").prop("checked",false);//取消全选
		}
	})
	
	//返回
	$("#back_role").click(function() {
		//查询页面保持保持
		$("#back_form").submit();
	})
	
	 //bootstrapValidator校验
	 $('#role_form').bootstrapValidator({
         feedbackIcons: {/*input状态样式图片*/
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
        	 name:{
        		 trigger:"blur",
        		 validators:{
        			 message: '请输入正确的用户名',
        			 notEmpty:{
        				 message: '用户姓名不能为空'
        			 },
        			 stringLength: {
        		          min: 2,
        		          max: 15,
        		          message: '用户姓名不能小于2位'
        		     }
        		 }
        	 },
        	 code:{
        		 trigger:"blur",
        		 validators:{
        			 notEmpty:{
        				 message: '角色编码不能为空'
        			 },
		        	 stringLength: {
				          min: 3,
				          max: 15,
				          message: '角色编码不能小于3位'
				     }
        		 }
        	 }
         }
	 })
	 
	 //重复时返回页面保持勾选状态
	 if($("#funcs").val()!=""){
		 
		var str = $("#funcs").val().split(",");
		for(var i=0;i<str.length;i++){
			$(".functions"+str[i]).prop("checked",true);
		}
		
		//中间值，用于页面加载时判断是否所有的选项都被选了,如果所有选项都被选中，那么也选中全选按钮
		var count = 1;
		
		$("#roleMenuDiv").find("input[name='checkbox']").each(function () {
	        if (!$(this).is(":checked")) {
	            count=0;
	        }
	    })
	    
	    if(count==1){
	    	$("#check_all").prop("checked",true);
	    }
	 }
	
	 //去除form表单的disabled状态
	 $("#roleMenuDiv").click(function() {
		$("#myfs").removeAttr('disabled');
	 })
	//结束
})
</script>