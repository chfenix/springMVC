<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
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
    <section class="content-header">
      <h1>
        Shiro示例
        <small>Shiro使用示例</small>
      </h1>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">
	<div class="row"> 
        <div class="col-md-12">
          <div class="box box-primary">
            <div class="box-header">
              <i class="fa fa-edit"></i>
              <h3 class="box-title">登录用户信息</h3>
            </div>
            <div class="box-body pad table-responsive">
              <table class="table table-bordered text-center">
                <tbody>
                <tr>
                  <td>登录名:<shiro:principal property="userName" /></td>
                  <td>姓名:<shiro:principal property="name" /></td>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
              </tbody></table>
            </div>
            <!-- /.box -->
          </div>
           
          <div class="box box-primary">
            <div class="box-header">
              <i class="fa fa-edit"></i>
              <h3 class="box-title">权限控制</h3>
            </div>
            <div class="box-body pad table-responsive">
              <table class="table table-bordered text-center"> 
                <tbody>
                <tr>
                  <td>包含权限：<shiro:hasPermission name="sample:shiro"> 拥有sample:shiro权限</shiro:hasPermission></td>
                  <td>无权限：<shiro:lacksPermission name="sample:test"> 无sample:test权限</shiro:lacksPermission> </td>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
              </tbody></table>
            </div>
            <!-- /.box -->
          </div>
        </div>
        <!-- /.col -->
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	</jsp:body>
</tags:template>