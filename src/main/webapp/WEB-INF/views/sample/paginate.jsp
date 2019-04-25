<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sw" uri="/WEB-INF/solwind.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:template>
	<jsp:attribute name="head">  
		<script type="text/javascript">
			// inline JavaScript here 
		</script>
  	</jsp:attribute>  
	<jsp:body>
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
<!-- 正文区域 -->
            <section class="content">
  			<!-- 操作反馈 -->
                <!-- .box-body -->
                <div class="box box-primary">
                    <div class="dataTables_filter" id="searchDiv">
                    <form action="paginate.do" id="frmQuery" method="post">
                    
	                    <!-- 隐藏域 -->
	                    <!-- 分页用页码 -->
	                    <input type="hidden" name="pageNum" id="pageNum" value="${pageBean.curPage }" />
					    
				   </form>
				  </div>
                    <div class="box-body" style="padding-top: 0px;">
                        <!-- 数据表格 -->
                        <div class="table-box">
                            <!--数据列表-->
                            <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>评论编码</th>
		                                <th>店铺编码</th>
		                                <th>星级</th>
		                                <th>内容</th>
							       </tr> 
                                </thead>
                                <tbody>
                                    <c:forEach var="item" varStatus="i" items="${pageBean.data}">
									    <tr>
										    <td>${item.rateNo}</td>
										    <td>${item.shopNo}</td>
										    <td>${item.rating}</td>
										    <td>${item.rateContent}</td>
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
            </section>
            <!-- 正文区域 /-->
  </div>
  <!-- /.content-wrapper -->
	</jsp:body>
</tags:template>