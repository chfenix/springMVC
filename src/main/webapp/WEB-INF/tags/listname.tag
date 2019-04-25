<%@tag pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="listname" uri="http://www.solwind.cn/springMVC/listnametag" %>
<%@attribute name="listId" rtexprvalue="true" %>
<%@attribute name="typeCode" required="true" rtexprvalue="true"%>
<%@attribute name="listCode" required="true" rtexprvalue="true"%>
<hylistname:listname/>
<span id="${listId}">${listName}</span>