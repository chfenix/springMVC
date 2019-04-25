<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sw" uri="/WEB-INF/solwind.tld"%>
<tags:template>
	<jsp:body>
	 <div class="content-wrapper">
		<div id="alert_danger" class="callout callout-danger" style="display: none;"></div>
	  	<!-- 内容头部 -->
	    <section class="content-header">
	      <h1>
	        用户管理
	        <small>用户信息添加</small>
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
			            <form  action="addUserInfo.do" method="post" onsubmit="return epJudge();" id="user_form" name="user_form" class="form-horizontal bv-form" novalidate="novalidate">
			            	<!-- 隐藏域 -->
			            	<input type="hidden" id="username_search" name="username_search" value="${username_search}">
			            	<input type="hidden" id="name_search" name="name_search" value="${name_search}">
			            	<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}">
			            	
			            	<!-- 用于返回roleid的字符串，后台分割成数组进行操作  -->
			            	<input type="hidden" id="roleIds" name="roleIds" value="${roleIds}">
			            	<!-- 隐藏域 -->
			                <div class="box-body">
			                    <div class="row">
			                        <div class="col-md-6">
			                        	<!-- 左侧  -->
			                             <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">用户登录名<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <input type="text" class="form-control" id="userName" name="userName" data-bv-field="userName" value="${ui.userName }" maxlength="20">
			                                </div>
			                            </div>
			                            <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">用户姓名<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <input type="text" class="form-control" id="name" name="name" data-bv-field="name" value="${ui.name }">
			                                </div>
			                            </div>
			                            <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">密码<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <input type="text" placeholder="请输入新密码" class="form-control" id="password" name="password" data-bv-field="password">
			                                </div>
			                            </div>
				                        
			                            <!-- /.左侧  -->
			                        </div>
			                        
			                        <div class="col-md-6">
			                        	<!-- 右侧  -->
			                        	<div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">手机</label>
			                                <div class="col-sm-8">
			                                    <input type="text" class="form-control" id="mobile" name="mobile" data-bv-field="mobile" value="${ui.mobile }" oninput="value=value.replace(/[^\d]/g,'')" maxlength="11">
			                                </div>
			                            </div>
			                            <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">email</label>
			                                <div class="col-sm-8">
			                                    <input type="text" class="form-control" id="email" name="email" data-bv-field="email" value="${ui.email }">
			                                </div>
			                            </div>
			                            <div class="form-group has-feedback">
			                                <label class="col-sm-4 control-label">状态<span style="color:red">*</span></label>
			                                <div class="col-sm-8">
			                                    <tags:select selectId="status" selectName="status" typeCode="COMMON_VALID"  selectValue="1" selectStyle="width: 100%;height: 34px;padding: 6px 12px;border: 1px solid #d2d6de;"></tags:select>
			                                </div>
			                            </div>
			                            <input type="hidden" id="id" name="id" value="${ui.id }">
			                        	
			                        	<!-- /.右侧  -->
			                        </div>
			                    </div>
			                </div>
			                
			                <div id="userRoleRelation" class="box-body" style="padding-left: 60px;">
			                	<div class="form-group has-feedback">
						        	<div class="col-sm-8">
	                                  <label style="height: 29px;padding-top: 6px;font-size: 15px;">请为用户选择下方的角色：</label>
	                                </div>
	                            </div>
	                            <c:forEach var="item" varStatus="i" items="${srList}">
	                            		<div class="col-md-6" style="width: 23%;padding-left: 50px;">
				                        	 <div class="form-group has-feedback" style="width: 100%">
									        	<div class="col-sm-8" style="padding-top: 6px;width: 100%;float: left;">
				                                   <input type="checkbox" id="roleId${item.id}" name="checkbox"  value="${item.id}" style="float: left;margin-top: 5px;" >
				                                   <label class="col-sm-4" style="padding-left: 5px;font-size: 15px;width: 80%">${item.name}</label>
				                                </div>
				                            </div>
		                          		</div>
	                            </c:forEach>
			                </div>
			                
			                
			                <div class="box-footer text-right">
			                    <button id="back_user" type="button" class="btn btn-default" data-btn-type="cancel"><i class="fa fa-reply">&nbsp;返回</i></button>
			                    <button id="myfs" type="submit" class="btn btn-primary" data-btn-type="save"><i class="fa fa-save">&nbsp;提交</i></button>
			                </div>
			            </form>
			            <!-- 隐藏form  -->
			            <form id="back_form" action="goBackUser.do" method="post">
			            	<input type="hidden" name="userName" value="${username_search}">
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
	
	var password = $("#password").val().trim();
	var userName = $("#userName").val().trim();
	var name = $("#name").val().trim();
	
	//必填项判断
	if(name==""||userName==""||password==""){
		return false;
	}
	//将勾选的角色数据返回
	getRoleIds();
	
	return true;
}

//获取选择的角色，然后存放进form表单，用户提交时返回
function getRoleIds() {
	var str="";
	$("#userRoleRelation").find("input[name='checkbox']").each(function () {
        if ($(this).is(":checked")) {
            //alert($(this).val());
            str += $(this).val()+",";
        }
    })
    str = str.substring(0,str.length-1);
	$("#roleIds").attr("value",str);
}

$(function(){
	//登录名称重复之后提示框
	if("${respcode}"!=""){
		$("#alert_danger").css('display','block');
		$("#alert_danger").html("${respmsg}");
		setTimeout(function(){$("#alert_danger").css("display","none");$("#alert_danger").html("");}, 2000);
	}
	
	 //bootstrapValidator校验
	 $('#user_form').bootstrapValidator({
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
        		          max: 25,
        		          message: '用户姓名不能小于2位或超过25位'
        		     }
        		 }
        	 },
        	 userName:{
        		 trigger:"blur",
        		 validators:{
        			 notEmpty:{
        				 message: '用户登录名不能为空'
        			 },
		        	 stringLength: {
				          min: 2,
				          max: 25,
				          message: '用户登录名不能小于2位或超过25位'
				     },
                	 threshold:2,
                	 remote:{
                		 trigger:"blur",
                		 data:{id:$('input[name="id"]').val()},
                		 url:"userNameDuplicate.do",
                		 message:"用户名已存在",
                		 type:"post"
                	 }
        		 }
        	 },
        	 password:{
        		 trigger:"blur",
        		 validators:{
        			 notEmpty:{
        				 message: '用户密码不能为空'
        			 },
        			 stringLength: {
				          min: 6,
				          max: 20,
				          message:'请输入6-20位密码'
				     }
        		 }
        	 },
        	 email: {
                 validators: {
                     emailAddress: {
                         message: '请输入正确的邮件地址如：123@qq.com'
                     }
                 }
             },
             mobile: {
                 validators: {
                     regexp: {
                         regexp: /^1[3,4,5,6,7,8,9]\d{9}$/,
                         message:'请输入正确的手机号码'
                     }
                 }
             }
         }
	 })
	 
	 //去除提交按钮disabled状态
	 $("#userRoleRelation").click(function() {
		$("#myfs").removeAttr('disabled');
	 })
	
	//返回
	$("#back_user").click(function() {
		//查询页面保持保持
		$("#back_form").submit();
	})
	
	 //重复时返回页面保持勾选状态
	 if($("#roleIds").val()!=""){
		 
		var str = $("#roleIds").val().split(",");
		for(var i=0;i<str.length;i++){
			$("#roleId"+str[i]).prop("checked",true);
		}
	 }
	//结束
})
</script>