<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="cn.solwind.admin.common.LoginSummary"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<c:url value="/" var="baseUrl" />
<c:set value="${fn:length(baseUrl)}" var="baseUrlLen" />
<c:set var="baseUrl" value="${fn:substring(baseUrl, 0, baseUrlLen - 1)}" />
	<link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/morris/morris.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/datepicker/datepicker3.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/treeTable/jquery.treetable.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/treeTable/jquery.treetable.theme.default.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/select2/select2.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/adminLTE/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/css/style.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/ionslider/ion.rangeSlider.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/ionslider/ion.rangeSlider.skinNice.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap-slider/slider.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap-validator/css/bootstrap-validator.css"/>
    
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/fileinput/fileinput.min.css">
    
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/timepicker/bootstrap-timepicker.min.css">
    
    
	<script src="${baseUrl}/resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${baseUrl}/resources/plugins/jQueryUI/jquery-ui.min.js"></script>
    <script>
        $.widget.bridge('uibutton', $.ui.button);
    </script>
    <script src="${baseUrl}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseUrl}/resources/plugins/raphael/raphael-min.js"></script>
    <script src="${baseUrl}/resources/plugins/morris/morris.min.js"></script>
    <script src="${baseUrl}/resources/plugins/sparkline/jquery.sparkline.min.js"></script>
    <script src="${baseUrl}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="${baseUrl}/resources/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <script src="${baseUrl}/resources/plugins/knob/jquery.knob.js"></script>
    <script src="${baseUrl}/resources/plugins/daterangepicker/moment.min.js"></script>
    <script src="${baseUrl}/resources/plugins/daterangepicker/daterangepicker.js"></script>
    <script src="${baseUrl}/resources/plugins/daterangepicker/daterangepicker.zh-CN.js"></script>
    <script src="${baseUrl}/resources/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script src="${baseUrl}/resources/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <script src="${baseUrl}/resources/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <script src="${baseUrl}/resources/plugins/fastclick/fastclick.js"></script>
    <script src="${baseUrl}/resources/plugins/iCheck/icheck.min.js"></script>
    <script src="${baseUrl}/resources/plugins/adminLTE/js/app.min.js"></script>
    <script src="${baseUrl}/resources/plugins/treeTable/jquery.treetable.js"></script>
    <script src="${baseUrl}/resources/plugins/select2/select2.full.min.js"></script>
    <script src="${baseUrl}/resources/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.zh-CN.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-markdown/js/bootstrap-markdown.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-markdown/locale/bootstrap-markdown.zh.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-markdown/js/markdown.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-markdown/js/to-markdown.js"></script>
    <script src="${baseUrl}/resources/plugins/ckeditor/ckeditor.js"></script>
    <script src="${baseUrl}/resources/plugins/input-mask/jquery.inputmask.js"></script>
    <script src="${baseUrl}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
    <script src="${baseUrl}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
    <script src="${baseUrl}/resources/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="${baseUrl}/resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${baseUrl}/resources/plugins/chartjs/Chart.min.js"></script>
    <script src="${baseUrl}/resources/plugins/flot/jquery.flot.min.js"></script>
    <script src="${baseUrl}/resources/plugins/flot/jquery.flot.resize.min.js"></script>
    <script src="${baseUrl}/resources/plugins/flot/jquery.flot.pie.min.js"></script>
    <script src="${baseUrl}/resources/plugins/flot/jquery.flot.categories.min.js"></script>
    <script src="${baseUrl}/resources/plugins/ionslider/ion.rangeSlider.min.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-slider/bootstrap-slider.js"></script>
	<script src="${baseUrl}/resources/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${baseUrl}/resources/plugins/bootstrap-validator/js/bootstrap-validator.min.js"></script>
    
	<script src="${baseUrl}/resources/plugins/fileinput/fileinput.min.js"></script>
	<script src="${baseUrl}/resources/plugins/fileinput/zh.js"></script>
	
	<script src="${baseUrl}/resources/plugins/timepicker/bootstrap-timepicker.min.js"></script>
	
	
<style>
.paramDiv {
	width: 100%;
	height: 60px;
	//text-align: center; 
	border: solid 1px #fff;
	line-height: 60px;
}

.input {
	width: 200px;
	height: 30px;
	margin-top: 15px;
}

lable{
	font-size: 14px;
}
</style>
		<!-- 页面头部 -->
        <header class="main-header">
            <!-- Logo -->
            <a class="logo">
                <!-- mini logo for sidebar mini 50x50 pixels --> 
                <span class="logo-mini">
                <img src="${baseUrl}/resources/img/logo.jpg"  height="50px" weigth="50px"  alt="业务">
                </span>
                <!-- logo for regular state and mobile devices -->
                <span class="logo-lg"><b>业务</b>管理系统</span>
            </a>
            <!-- Header Navbar: style can be found in header.less -->
            <nav class="navbar navbar-static-top">
                <!-- Sidebar toggle button-->
                <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            		<span class="sr-only">Toggle navigation</span>
       			</a>

                <div class="navbar-custom-menu">
                    <ul class="nav navbar-nav">
                        <!-- User Account: style can be found in dropdown.less -->
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        		<span class="hidden-xs"><shiro:principal property="name" /></span>
                        		<i class="fa fa-gears"></i>
                    		</a>
                            <ul class="dropdown-menu">
                                <!-- User image -->
                                <li class="user-header">
                                    <img src="${baseUrl}/resources/img/logo.jpg" class="img-circle" alt="业务">

                                    <p>
                                    	<shiro:principal property="name" />
                                        <small>最后登录
                         				<% 
                         					Subject subject = SecurityUtils.getSubject();
                         					LoginSummary summary = (LoginSummary) subject.getPrincipal();
                         					Date loginTime=summary.getLoginTime(); 
                                        %> 
                                        <fmt:formatDate value="<%=loginTime%>" pattern="yyyy-MM-dd HH:mm:ss"/>
                                  		</small>
                                    </p>
                                </li>

                                <!-- Menu Footer-->
                                <li class="user-footer">
                                    <div class="pull-left">
                                        <a href="#myModal" data-toggle="modal" id="updatePwd" class="btn btn-default btn-flat">修改密码</a>
                                    </div>
                                    <div class="pull-right">
                                        <a href="${baseUrl}/logout.do" class="btn btn-default btn-flat">注销</a>
                                    </div>
                                </li>
                            </ul>
                        </li>

                    </ul>
                </div>
                
            </nav>

</header>
<!-- 页面头部 /-->

  <!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
		aria-hidden="true" style="width:40%;height:550px;border:solid 1px #fff;position:fixed;
		top:0px;left:0px;right:0px;bottom:0px;margin:auto;border-radius: 8px;border:solid 1px #ccc;
		background-color: #fff;">
		<div class="callout" id="hintMsg" style="display:none;">
        		<p><b></b></p>
    	</div>
		<form id="updatePwdForm" name="updatePwdForm" method="post" action=""> 
			<div class="paramDiv" style="width:100%;height:50px;line-height:50px;font-size:14px;padding: 0 15px;border-bottom: 1px solid #F4F4F4;">
				<span>修改密码</span>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close" 
					style="height:51px;line-height:51px;text-align:center;">
					<span aria-hidden="true" id="closePwdModal">×</span>
				</button>
			</div>
			
			<div class="box-header with-border">
				<ul>
			       <li>
			          <span style="color:red">*</span>为必填选项
			       </li>
			    </ul>
			</div>
			
			<div class="row" style="width:100%;height:320px;">
			<div class="col-md-12" style="margin-top:20px;">
			   <div class="form-group has-feedback">
			         <label class="col-sm-4 control-label" style="text-align:right;">姓名&nbsp;&nbsp;</label>
			         <div class="col-sm-6">
			             <input type="text" name="username" id="username" value="<shiro:principal property="userName" />" class="form-control" data-bv-field="username" readonly/>
			         </div>
			    </div>
			</div>	
			
			<div class="col-md-12" style="margin-top:20px;">
			   <div class="form-group has-feedback" id="div_oldPassword">
			         <label class="col-sm-4 control-label" style="text-align:right;">原密码<span style="color: red;">*</span></label>
			         <div class="col-sm-6">
			             <input type="password" name="oldPassword" id="oldPassword" maxlength="15" class="form-control" data-bv-field="oldPassword" placeholder="请输入密码" />
			        	 <i id="i_oldPassword" class="form-control-feedback" data-bv-icon-for="oldPassword" maxlength="15" style="margin-right:15px;"></i>
			             <small id="small_oldPassword" data-bv-validator="notEmpty" data-bv-validator-for="oldPassword" class="help-block" style="display: none;">原密码不能为空</small>
			         </div>
			    </div>
			</div>
			
			
			<div class="col-md-12" style="margin-top:20px;">
			   <div class="form-group has-feedback" id="div_newPassword">
			         <label class="col-sm-4 control-label" style="text-align:right;">新密码<span style="color: red;">*</span></label>
			         <div class="col-sm-6">
			             <input type="password" name="newPassword" id="newPassword" maxlength="15" class="form-control" data-bv-field="newPassword" placeholder="请输入新密码" />
			         	 <i id="i_newPassword" class="form-control-feedback" data-bv-icon-for="newPassword" maxlength="15" style="margin-right:15px;"></i>
			             <small id="small_newPassword" data-bv-validator="notEmpty" data-bv-validator-for="newPassword" class="help-block" style="display: none;">新密码不能为空</small>
			         </div>
			    </div>
			</div>
			
			
			<div class="col-md-12" style="margin-top:20px;">
			   <div class="form-group has-feedback" id="div_confirmPwd">
			         <label class="col-sm-4 control-label" style="text-align:right;">确认新密码<span style="color: red;">*</span></label>
			         <div class="col-sm-6">
			             <input type="password" name="confirmPwd" id="confirmPwd" maxlength="15" class="form-control" data-bv-field="confirmPwd" placeholder="请输入确定新密码" />
			         	 <i id="i_confirmPwd" class="form-control-feedback" data-bv-icon-for="confirmPwd" maxlength="15" style="margin-right:15px;"></i>
			             <small id="small_confirmPwd" data-bv-validator="notEmpty" data-bv-validator-for="confirmPwd" class="help-block" style="display: none;">确认新密码不能为空</small>
			         </div>
			    </div>
			</div>	
			<br>	
			</div>
			
			<div class="modal-footer" style="text-align: center;">
				<div id="operDiv">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="closePwdBtn">
<!-- 						<i class="fa fa-remove"></i> -->
						取消
					</button>
					<button type="button" id="updatePwdBtn" class="btn btn-primary">
<!-- 						<i class="fa fa-save"></i> -->
						提交
					</button>
				</div>
				<div style="display:none;" id="closeDiv">
					<button type="button" class="btn btn-default" data-dismiss="modal" class="closeModalBtn">
						关闭
					</button>
				</div>
			</div>
		</form>
</div><!-- /.modal -->



<script type="text/javascript">
$(function() {
		function removeFormClass(operation) {
			var operationSmall = "#small_"+operation;
			var operationDiv = "#div_"+operation;
			var operationI = "#i_"+operation;
			$(operationSmall).css("display","none");
		    if($(operationDiv).hasClass("has-error")){
				$(operationDiv).removeClass("has-error");
			}
			if($(operationI).hasClass("glyphicon-remove")){
				$(operationI).removeClass("glyphicon glyphicon-remove");
			}
			if($(operationI).hasClass("glyphicon-ok")){
				$(operationI).removeClass("glyphicon glyphicon-ok");
			}
		}	
		
		
		function closeCurrModal(){
			$("#myModal").modal('hide');
		}
		
		//ajax修改密码
  		function ajaxUpdatePwd(oldPassword,newPassword){
  			$.ajax({
  		        url:'${baseUrl}/updatePwd.do',
  		        data:{
  		        	oldPassword : oldPassword,
  		        	newPassword : newPassword,
  		        },
  				traditional: true,//必须
  		        type:'post',
  				dataType:'json',
  		        success:function(res){
  		        	 $("#oldPassword").val("");
  		        	 $("#newPassword").val("");
					 $("#confirmPwd").val("");
					 //密码样式恢复原形	       		 
		 			 $('#small_oldPassword').css('display','none');
		 			 $("#div_oldPassword").removeClass("has-feedback has-success");
		    		 $("#i_oldPassword").removeClass("glyphicon glyphicon-ok");
		    		 $('#small_newPassword').css('display','none');
		 			 $("#div_newPassword").removeClass("has-feedback has-success");
		    		 $("#i_newPassword").removeClass("glyphicon glyphicon-ok");
		    		 $('#small_confirmPwd').css('display','none');
		 			 $("#div_confirmPwd").removeClass("has-feedback has-success");
		    		 $("#i_confirmPwd").removeClass("glyphicon glyphicon-ok");
					 
  		        	 $("#hintMsg p").html(res.respmsg);
    		         if(res.respcode!="" && res.respcode=="0000"){
    		        	 $("#hintMsg").removeClass("callout-danger");
  	  				 	 $("#hintMsg").addClass("callout-success");
    		        	 $("#hintMsg").show();
    		        	 
    		        	 $("#operDiv").hide();
    		        	 $("#closeDiv").show();
    		        	 
    		        	//成功，提示3s，关闭修改密码modal
 					    setTimeout(closeCurrModal, 3000);
    		         }else{
    		        	 $("#hintMsg").removeClass("callout-success");
  	  				 	 $("#hintMsg").addClass("callout-danger");
    		        	 $("#hintMsg").show();
    		         }
  		        }
  		    })
  		}
		
		
		
		 //修改密码
		 $("#updatePwdBtn").click(function(){
			 $("#hintMsg p").html("");
	  		 $("#hintMsg").hide();
			 var oldPassword=$("#oldPassword").val();
			 var newPassword=$("#newPassword").val();
			 var confirmPwd=$("#confirmPwd").val();
			 if(oldPassword!="" && newPassword!="" && confirmPwd!=""){
				 //原密码和新密码一致
				 if(oldPassword==newPassword){
					 $("#small_newPassword").html("新密码不能和旧密码一致");
					 $('#small_newPassword').css('display','block');
			 		 $("#div_newPassword").addClass("has-feedback has-error");
			 		 $("#i_newPassword").addClass("glyphicon glyphicon-remove");
			 		 $("#small_confirmPwd").html("确认新密码不能和旧密码一致");
					 $('#small_confirmPwd').css('display','block');
			 		 $("#div_confirmPwd").addClass("has-feedback has-error");
			 		 $("#i_confirmPwd").addClass("glyphicon glyphicon-remove");
					 $("#newPassword").val("");
					 $("#confirmPwd").val("");
				 }else if(newPassword!=confirmPwd){
					 //新密码和确认新密码不一致
					 $("#small_newPassword").html("新密码和确认新密码不一致");
					 $('#small_newPassword').css('display','block');
			 		 $("#div_newPassword").addClass("has-feedback has-error");
			 		 $("#i_newPassword").addClass("glyphicon glyphicon-remove");
			 		 $("#small_confirmPwd").html("新密码和确认新密码不一致");
					 $('#small_confirmPwd').css('display','block');
			 		 $("#div_confirmPwd").addClass("has-feedback has-error");
			 		 $("#i_confirmPwd").addClass("glyphicon glyphicon-remove");
			 		 $("#newPassword").val("");
					 $("#confirmPwd").val("");
				 }else{
					 //修改密码
					 //$("#updatePwdForm").attr("action","${baseUrl}/updatePwd.do");
				 	 //$("#updatePwdForm").submit();
				 	 ajaxUpdatePwd(oldPassword,newPassword);
				 }
			 }else{
				if(oldPassword==""){
					$('#small_oldPassword').css('display','block');
		 		 	$("#div_oldPassword").addClass("has-feedback has-error");
		 		  	$("#i_oldPassword").addClass("glyphicon glyphicon-remove");
				}else{
					removeFormClass("oldPassword");
		 			$("#div_oldPassword").addClass("has-feedback has-success");
		   		  	$("#i_oldPassword").addClass("glyphicon glyphicon-ok");
				}
				if(newPassword==""){
					$('#small_newPassword').css('display','block');
		 		 	$("#div_newPassword").addClass("has-feedback has-error");
		 		  	$("#i_newPassword").addClass("glyphicon glyphicon-remove");
				}else{
					removeFormClass("newPassword");
		 			$("#div_newPassword").addClass("has-feedback has-success");
		   		  	$("#i_newPassword").addClass("glyphicon glyphicon-ok");
				}
				if(confirmPwd==""){
					$('#small_confirmPwd').css('display','block');
		 		 	$("#div_confirmPwd").addClass("has-feedback has-error");
		 		  	$("#i_confirmPwd").addClass("glyphicon glyphicon-remove");
				}else{
					removeFormClass("confirmPwd");
		 			$("#div_confirmPwd").addClass("has-feedback has-success");
		   		  	$("#i_confirmPwd").addClass("glyphicon glyphicon-ok");
				}
			 }
		})
		
		
		
	$("#oldPassword").blur(function(){
		var oldPassword=$("#oldPassword").val();
		if(oldPassword==""){
		  	$('#small_oldPassword').css('display','block');
		 	$("#div_oldPassword").addClass("has-feedback has-error");
		  	$("#i_oldPassword").addClass("glyphicon glyphicon-remove");
		}else{
			removeFormClass("oldPassword");
			$("#div_oldPassword").addClass("has-feedback has-success");
   		  	$("#i_oldPassword").addClass("glyphicon glyphicon-ok");
		}
	})	
	
	
	$("#newPassword").blur(function(){
		var newPassword=$("#newPassword").val();
		if(newPassword==""){
		  	$('#small_newPassword').css('display','block');
		 	$("#div_newPassword").addClass("has-feedback has-error");
		  	$("#i_newPassword").addClass("glyphicon glyphicon-remove");
		}else{
			removeFormClass("newPassword");
			$("#div_newPassword").addClass("has-feedback has-success");
   		 	$("#i_newPassword").addClass("glyphicon glyphicon-ok");
		}
	})	
	$("#confirmPwd").blur(function(){
		var confirmPwd=$("#confirmPwd").val();
		if(confirmPwd==""){
		  	$('#small_confirmPwd').css('display','block');
		 	$("#div_confirmPwd").addClass("has-feedback has-error");
		  	$("#i_confirmPwd").addClass("glyphicon glyphicon-remove");
		}else{
			removeFormClass("confirmPwd");
			$("#div_confirmPwd").addClass("has-feedback has-success");
   		 	$("#i_confirmPwd").addClass("glyphicon glyphicon-ok");
		}
	})	
	
	
	//关闭按钮
	$("#closePwdBtn,#closePwdModal").click(function(){
		removeFormClass("oldPassword");
		removeFormClass("newPassword");
		removeFormClass("confirmPwd");
		$("#hintMsg p").html("");
		$("#hintMsg").hide();
		//$("#myModal").modal('hide');
		
	})
	
	//点击原密码、新密码、确认新密码、关闭按钮、修改按钮，隐藏提示框
	$("#oldPassword,#newPassword,#confirmPwd").click(function(){
		$("#hintMsg p").html("");
		$("#hintMsg").hide();
	})
	
	//用户登录信息
	$("#userInfoUl").click(function(){
		$("#userInfoUl").hide();
	})
	
	//模态框可拖拽
	$("#myModal").draggable();
	$("#myModal").css("overflow", "hidden");
	
	//修改密码按钮
	$("#updatePwd").click(function(){
		$("#hintMsg p").html("");
		$("#hintMsg").hide();
		
		$("#operDiv").show();
		$("#closeDiv").hide();
	})
})
</script>

