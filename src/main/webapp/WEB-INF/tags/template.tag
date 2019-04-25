<%@tag pageEncoding="UTF-8" isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<jsp:directive.attribute name="head" required="false" fragment="true" />
<html>
<head>
<meta charset="utf-8">
<title>业务管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:url value="/" var="baseUrl" />
<c:set value="${fn:length(baseUrl)}" var="baseUrlLen" />
<c:set var="baseUrl" value="${fn:substring(baseUrl, 0, baseUrlLen - 1)}" />

<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    
</head>
<body class="hold-transition skin-green sidebar-mini">
<div class="wrapper">
	<jsp:include page="/WEB-INF/views/common/head.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />
	<jsp:doBody />
	<jsp:include page="/WEB-INF/views/common/foot.jsp" />
</div>
    <script>
        $(document).ready(function() {
            // 选择框
            $(".select2").select2();

            // WYSIHTML5编辑器
            $(".textarea").wysihtml5({
                locale: 'zh-CN'
            });
        });


        // 设置激活菜单
        function setSidebarActive(tagUri) {
            var liObj = $("#" + tagUri);
            if (liObj.length > 0) {
                liObj.parent().parent().addClass("active");
                liObj.addClass("active");
            }
        }


        $(document).ready(function() {
            // 激活导航位置
            setSidebarActive("${currMenu}");
        });
    </script>
</body>
</html>