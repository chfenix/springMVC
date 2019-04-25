<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:url value="/" var="baseUrl" />
	<c:set value="${fn:length(baseUrl)}" var="baseUrlLen" />
	<c:set var="baseUrl" value="${fn:substring(baseUrl, 0, baseUrlLen - 1)}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>业务管理系统 | 登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <!-- Font Awesome -->
    <!-- Ionicons -->
    <!-- Theme style -->
    <!-- iCheck -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/bootstrap-validator/css/bootstrap-validator.css"/>
    <style>
        .background{
		   background: #cad8f3;
		   background:url(${baseUrl}/resources/img/login_background.jpg)  no-repeat center center;
		   background-size:cover;
		   background-attachment:fixed;
		}
    </style>
</head>

<body class="hold-transition login-page background" >
    <div class="login-box">
        <div class="login-logo">
            <a href="all-admin-index.html"  style="color:#000;"><b>业务</b>管理系统</a>
        </div>
        <!-- /.login-logo -->
        <div class="login-box-body">
            <p class="login-box-msg">登录系统</p>
            <form action="login.do" method="post" id="login-form" >
                <div class="form-group has-feedback">
                    <input type="text"  id="username"  name="username" class="form-control" placeholder="请输入用户名" maxlength="20" value="${username}">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control"  id="password"   name="password" placeholder="请输入密码" maxlength="20" value="${password}">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="form-group ">
	                <div class="col-xs-4" style="padding:0px;width:35%;">
	                    <input type="text" class="form-control" name="captcha"  id="captcha" placeholder="请输入验证码"  maxlength="4">
	                    
	                </div>
	                <div class="col-xs-5" style="text-align:right;padding-right: 0px;width:40%;">
	                    <img id="randCodeImage" src="captcha.do"  class="img-responsive"
	                         style="height: 35px;">
	                </div>
	                <div class="col-xs-3" style="padding-top:10px;width:25%;">
	                    <span><a href="#" onclick="changeCaptcha()">换一换</a></span>
	                </div>
	            </div>
	            <div class="row" style="margin-top:70px;">
                <div class="col-xs-6">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox" name="rememberMe"> 记住用户
                        </label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-danger btn-block btn-flat" >登 录</button>
                </div>
            </div>
		</form>
        </div>
        <!-- /.login-box-body -->
    </div>
    <!-- /.login-box -->

    <!-- jQuery 2.2.3 -->
    <!-- Bootstrap 3.3.6 -->
    <!-- iCheck -->
    <script src="${baseUrl}/resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseUrl}/resources/plugins/iCheck/icheck.min.js"></script>
    <script src="${baseUrl}/resources/plugins/bootstrap-validator/js/bootstrap-validator.min.js"></script>
    <script src="${baseUrl}/resources/js/login.js"></script>
    <script>
    //刷新验证码
    function changeCaptcha(){
    	var rad = Math.floor(Math.random() * Math.pow(10, 8));
        $("#randCodeImage").attr("src", "captcha.do?random="+rad);
        $("#captcha").val("");
    }
    
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-red',
            radioClass: 'iradio_square-red',
            increaseArea: '20%' // optional
        });

        fillbackLoginForm();
        $("#login-form").bootstrapValidator({
            message: '请输入用户名/密码',
            submitHandler: function (valiadtor, loginForm, submitButton) {
                rememberMe($("input[name='rememberMe']").is(":checked"));
                valiadtor.defaultSubmit();
            },
            fields: {
            	username: {
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                },
                captcha:{
                    validators: {
                        notEmpty: {
                            message: '验证码不能为空'
                        },
                        callback: {
                    		message: ''
                    	}
                    }
                }
            }
        });

        <c:if test="${result != null}">
	        new LoginValidator({
	            code: "${result.code}",
	            message: "${result.message}",
	            username: 'username',
	            password: 'password',
	            captcha: 'captcha'
	        });
	        $("button[type='submit']").attr('disabled', 'disabled');
	        </c:if>
    });
    
    //使用本地缓存记住用户名密码
    function rememberMe(rm_flag) {
        //remember me
        if (rm_flag) {
            localStorage.username = $("input[name='username']").val();
            localStorage.password = $("input[name='password']").val();
            localStorage.rememberMe = 1;
        }
        //delete remember msg
        else {
            localStorage.username = null;
            localStorage.password = null;
            localStorage.rememberMe = 0;
        }
    }

    //记住回填
    function fillbackLoginForm() {
        if (localStorage.rememberMe && localStorage.rememberMe == "1") {
            $("input[name='username']").val(localStorage.username);
            $("input[name='password']").val(localStorage.password);
            $("input[name='rememberMe']").iCheck('check');
            $("input[name='rememberMe']").iCheck('update');
        }
    }

    $(function() {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
    
</body>

</html>