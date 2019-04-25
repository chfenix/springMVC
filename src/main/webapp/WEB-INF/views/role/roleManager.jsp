<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sw" uri="/WEB-INF/solwind.tld"%>
<tags:template>
	<jsp:body>
  <div class="content-wrapper">
  	<script type="text/javascript">
  	//修改角色
	function modifyRole(id){
		$("#id").attr("value",id);
		$("#operation").attr("value","modify");
		$("#role_form").attr("action","roleOperation.do");
		$("#role_form").submit();
	}
	
	//添加角色
	function addRole(){
		$("#operation").attr("value","add");
		$("#role_form").attr("action","roleOperation.do");
		$("#role_form").submit();
	}
	
	//删除角色
	function deleteRole(){
		$("#role_form").attr("action","deleteRoleInfo.do");
		$("#role_form").submit();
	}
	
	//用于点击模态框时传给模态框id
	function postId(id) {
		$("#id").val(id);
	}
	</script>
  <!-- 内容头部 -->
            <div id="alert_danger" class="callout" style="display: none;"></div>
    <section class="content-header">
      <h1>
          角色管理
        <small>角色查询</small>
      </h1>
    </section>
    <!-- 内容头部 /-->

<!-- 正文区域 -->
            <section class="content">
            <!-- 操作反馈 -->
  			<!-- 操作反馈 -->
                <!-- .box-body -->
                <div class="box box-primary">
                    <div class="dataTables_filter" id="searchDiv">
                    
                    <form action="queryRoleInfo.do" id="role_form" method="post">
                    
	                    <!-- 隐藏域 -->
	                    <!-- 分页用页码 -->
	                    <input type="hidden" name="pageNum" id="pageNum" value="${pageBean.curPage }" />
	                    
	                    <!-- 企业查询操作 -->
	                    <input type="hidden" id="id" name="id" value="">
	                    <input type="hidden" id="operation" name="operation">
	                    <!-- 隐藏域 -->
	                    
					    <!--查询条件--> 
					    <h4 class="pull-left text-gray">查询条件</h4> 
					    <input type="search" placeholder="请输入角色名称" title="角色名称" name="name" class="form-control" id="name" maxlength="30" value="${name}"/> 
					    <input type="search" placeholder="请输入角色编码" title="角色编码" name="code" class="form-control" id="code" maxlength="30" value="${code}"/>
					    
					    <div class="btn-group"> 
					    	<button type="submit" class="btn btn-primary" style="margin-left:8px;"><i class="fa fa-search"></i> 查询</button> 
					    	<button type="button" class="btn btn-default" onclick="addRole();"><i class="fa fa-plus"></i> 添加</button> 
					    </div> 
					    
				   </form>
				   
				  </div>
                    <div class="box-body" style="padding-top: 0px;">
                        <!-- 数据表格 -->
                        <div class="table-box">
                            <!--数据列表-->
                            <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>角色编码</th>
		                                <th>角色名称</th>
		                                <th>状态</th>
		                                <th class="text-center">操作</th>
							       </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" varStatus="i" items="${pageBean.data}">
									    <tr>
										    <td>${item.code}</td>
										    <td>${item.name}</td>
										    <td><tags:listname listId="status" typeCode="COMMON_VALID" listCode="${item.status}"/></td>
										    <td class="text-center">
										    	<button type="button" class="btn bg-orange btn-xs" onclick="modifyRole('${item.id}');">修改</button>
										    	<button type="button" class="btn bg-red btn-xs"  data-toggle="modal" data-target="#confirmModal" onclick="postId('${item.id}');">删除</button>
										    </td>
									    </tr>
									</c:forEach>
                                </tbody>
                            </table>
                    		<hy:paginate pageBean="${pageBean}" formId="frmQuery" />
                            <!--数据列表/-->
                        </div>
                        <!-- 数据表格 /-->
                    </div>
                    <!-- /.box-body -->
                    <!-- .box-footer-->
                    <!-- /.box-footer-->
                </div>
                	
                	<!-- 模态框 -->
                    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" 
							aria-labelledby="myModalLabel" aria-hidden="true" style="width:300px;height:160px;background:#fff;
							overflow-y:hidden!important;position:fixed;top:0px;left:0px;right:0px;
   							 bottom:0px;margin:auto;border-radius: 6px;">
							<div style="width:100%;height:51px;line-height:51px;font-size:18px;padding-left:5%;
								border-bottom:solid 1px #F4F4F4;">提示
								<button class="close" type="button" data-dismiss="modal" aria-label="Close" 
									style="height:51px;line-height:51px;text-align:center;padding:1% 5%;">
									<span aria-hidden="true">×</span>
								</button>
							</div>
<!-- 						<div class="modal-content"> -->
							<div style="width:100%;height:48px;line-height:48px;font-size:14px;padding-left:5%;
								border-bottom:solid 1px #F4F4F4;">
								确定删除该角色？
							</div>
							<div style="text-align: right;padding:5% 5%;">
								<button type="button" class="btn btn-default" data-dismiss="modal" id="btnClose"
									style="width:46px;height:30px;font-size:12px;text-align:center;maigin:0 auto;">
									取消
								</button>
								<button type="button" id="deleteBtn" class="btn btn-primary"
									style="width:46px;height:30px;font-size:12px;text-align:center;maigin:0 auto;" onclick="deleteRole()">
									确定
								</button>
							</div>
					</div>
					<!-- /.模态框  -->
            </section>
            <!-- 正文区域 /-->
  </div>
</jsp:body>
</tags:template>
<script type="text/javascript">
$(document).ready(function () {
	if("${respcode}"!=""){
		if("${respcode}"=="0000"){
			$("#alert_danger").css('display','block');
			$("#alert_danger").addClass("callout-success");
			$("#alert_danger").html("${respmsg}");
			setTimeout(function(){$("#alert_danger").css("display","none");$("#alert_danger").html("");$("#alert_danger").removeClass("callout-success");}, 2000);
		}else{
			$("#alert_danger").css('display','block');
			$("#alert_danger").addClass("callout-danger");
			$("#alert_danger").html("${respmsg}");
			setTimeout(function(){$("#alert_danger").css("display","none");$("#alert_danger").html("");$("#alert_danger").removeClass("callout-success");}, 2000);
		}
	};
	//菜单改变背景色
	$("#roleManager").addClass("active");
})
</script>
