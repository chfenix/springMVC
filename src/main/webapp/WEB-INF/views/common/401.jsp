<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:template>
	<jsp:attribute name="head">  
		<script type="text/javascript">
			// inline JavaScript here 
		</script>
  	</jsp:attribute>  
	<jsp:body>
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->

    <!-- Main content -->
    <section class="content">
        <div class="error-page">
            <h2 class="headline text-yellow"> 401</h2>

            <div class="error-content">
                <h3><i class="fa fa-warning text-yellow"></i> 未授权的操作.</h3>
                <p>
                   您没有权限操作此功能，请联系系统管理员!
                </p>
            </div>
            <!-- /.error-content -->
        </div>
        <!-- /.error-page -->
    </section>
    <!-- /.content -->
</div>

	</jsp:body>
</tags:template>