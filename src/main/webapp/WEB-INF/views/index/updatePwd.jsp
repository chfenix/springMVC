<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<c:url value="/" var="baseUrl" />
	  	<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/bootstrap/css/bootstrap.min.css"/>

		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery-jvectormap-1.2.2.min.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery-jvectormap-world-mill-en.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery-ui.min.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery.knob.min.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery.min.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery.slimscroll.min.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/adminlte/js/jquery.sparkline.min.js" ></script>
		<script type="text/javascript" src="${baseUrl}/resources/bootstrap/js/bootstrap.min.js" ></script>
		
<title>修改密码</title>
<style type="text/css">
	.paramDiv{
		width:100%;
		height:80px;
		text-align:center;
		//border:solid 1px blue;
		line-height:80px;
	}
	input{
		width:200px;
		height:30px;
		margin-top:25px;
	}
	.divCol1{
		width:30%;
		height:80px;
		float:left;
		text-align:right;
		//border:solid 1px red;
	}
	.divCol2{
		width:70%;
		height:80px;
		line-height:80px;
		//border:solid 1px red;
	}
	.updateBtn{
		width:100px;
		height:50px;
		margin-top:7%;
		border:none;
		font-size:18px;
		background: #1A8ED9;
	}
	.divHead{
		width:50%;
		height:600px;
		margin:0px auto;
		margin-top:5%;
		border:solid 1px #ccc;
	}
	#divHint{
		height:30px;
		margin-top:5%;
		display:none;
	}

}
</style>

<script type="text/javascript">
	
	 function update(){
		 var oldPassword=$("#oldPassword").val();
		 var newPassword=$("#newPassword").val();
		 var confirmPwd=$("#confirmPwd").val();
		 if(oldPassword!="" && newPassword!="" && confirmPwd!=""){
			 if(oldPassword==newPassword){
				 $("#spanHint").html("新密码不能与原密码相同");
				 $("#divHint").show();
				 $("#oldPassword").val("");
				 $("#newPassword").val("");
				 $("#confirmPwd").val("");
				 return;
			 }
 			 if(newPassword!=confirmPwd){
				 $("#spanHint").html("新密码不能与原密码相同");
				 $("#divHint").show();
				 $("#oldPassword").val("");
				 $("#newPassword").val("");
				 $("#confirmPwd").val("");
				 return;
			 }else{	
				$("#updatePwdForm").attr("action","<%=basePath%>index/updatePwd.do");
			 	$("#updatePwdForm").submit();
			 }
		 }
		 else{
			 return;
 		 }
	}
	 
</script>

<script type="text/javascript">
 $(function(){	
	//提示信息
	var respcode=${respcode};
	if(respcode!=""){
		if(respcode=="0000"){
			 $("#spanHint").html("修改成功");
		}
		if(respcode=="9901"){
			 $("#spanHint").html("修改失败");
		}
		if(respcode=="9902"){
			 $("#spanHint").html("原密码错误");
		}if(respcode=="9903"){
			 $("#spanHint").html("用户不存在");
		}
		 $("#divHint").show();
		 $("#oldPassword").val("");
		 $("#newPassword").val("");
		 $("#confirmPwd").val("");
	}
	
	//如果div提示显示，输入框获取焦点时，隐藏div提示,清空提示信息
	$("input").focus(function(){
		if(!$("#divHint").is(":hidden")){
			$("#divHint").hide();
			 $("#spanHint").html("");
		}
	})
	
	
 })

</script>

</head>
<body>
<div class="divHead">
<form id="updatePwdForm" name="updatePwdForm" method="post" action=""> 
	<div class="paramDiv" style="font-size:22px;background: #1A8ED9;">
	 	<span>修改密码</span>
	</div>
	<div class="paramDiv">
		<div class="divCol1">用户名：</div>
		<div class="divCol2">
			<input type="text" name="username" value="${sessionScope.username}" disabled="true"/>
		</div>
	</div>
	<div class="paramDiv">
		<div class="divCol1">原密码：<span style="color:red;">*</span></div>
		<div class="divCol2"><input type="password" name="oldPassword" id="oldPassword" maxlength="15" required /></div>
	</div>
	<div class="paramDiv">
		<div class="divCol1">新密码：<span style="color:red;">*</span></div>
		<div class="divCol2"><input type="password" name="newPassword" id="newPassword" maxlength="15" required /></div>
	</div>
	<div class="paramDiv">
		<div class="divCol1">确认新密码：<span style="color:red;">*</span></div>
		<div class="divCol2"><input type="password" name="confirmPwd" id="confirmPwd" maxlength="15" required /></div>
	</div>
	<div style="text-align: center;">
		<button class="updateBtn btn btn-default" onclick="javascript:history.go(-1);">返回</button>
		<button onclick="update()" class="updateBtn btn btn-default">修改</button>
	</div>
	
	<div id="divHint" class="alert alert-danger"><span id="spanHint">343434</span></div>
</form>
</div> 
</body>
</html>
