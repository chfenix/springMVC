<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:url value="/" var="baseUrl" />
<c:set value="${fn:length(baseUrl)}" var="baseUrlLen" />
<c:set var="baseUrl" value="${fn:substring(baseUrl, 0, baseUrlLen - 1)}" />
  <!-- 菜单 --> 
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar Menu --> 
      <ul class="sidebar-menu" data-widget="tree">
      <li class="header">菜单</li>
	<c:forEach items="${menu}" var="objMenu" >
		<c:choose>
		 <c:when test="${objMenu.children==null || fn:length(objMenu.children) == 0}">
		 <%-- 无子菜单 --%>
		 	<li id="${objMenu.func.id}"><a href="${baseUrl}${objMenu.func.action}"><i class="fa ${objMenu.func.iconClass}"></i> <span>${objMenu.func.name }</span></a></li>
		 </c:when>
		 <c:otherwise> 
		 <%-- 有子菜单 --%>
		 <li class="treeview"  id="${objMenu.func.id}">
          <a href="#"><i class="fa ${objMenu.func.iconClass}"></i> <span>${objMenu.func.name }</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
			<c:forEach items="${objMenu.children}" var="objChild" >
            <li  id="${objChild.id}"><a href="${baseUrl}${objChild.action }"><i class="fa ${objChild.iconClass }"></i>${objChild.name }</a></li>
            </c:forEach>
          </ul>
        </li>
		 </c:otherwise>
		</c:choose>
	</c:forEach>
			<!-- <li id="enterpriseManager"><a href="#"><i class="fa  fa-suitcase"></i> <span>企业管理</span></a></li>
        <li id="shopManager"><a href="#"><i class="fa fa-home"></i> <span>门店管理</span></a></li>
        <li><a href="#"><i class="fa  fa-user-plus"></i> <span>员工导入</span></a></li>
        <li><a href="#"><i class="fa fa-users"></i> <span>员工管理</span></a></li>
        <li><a href="#"><i class="fa  fa-gift"></i> <span>活动管理</span></a></li>
        <li><a href="#"><i class="fa  fa-sitemap"></i> <span>商户池管理</span></a></li>
        <li id="userManager"><a href="toUserInfo"><i class="fa  fa-users"></i> <span>用户管理</span></a></li> -->
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
