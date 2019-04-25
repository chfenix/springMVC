<%@tag pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="select" uri="http://www.solwind.cn/springMVC/selecttag" %>
<%@attribute name="selectId" rtexprvalue="true" %>
<%@attribute name="typeCode" required="true" rtexprvalue="true"%>
<%@attribute name="selectName" type="java.lang.String" rtexprvalue="true"%>
<%@attribute name="selectDesc" rtexprvalue="true"%>
<%@attribute name="selectValue" rtexprvalue="true"%>
<%@attribute name="selectStyle" rtexprvalue="true"%>
<%@attribute name="selectTitle" rtexprvalue="true"%>
<%@attribute name="readValue" rtexprvalue="true"%>
<hyselect:sysbook/>

<select id="${selectId}" name="${selectName}" style="${selectStyle}" title="${selectTitle}"
		data-flag="dictSelector" data-code="NATION" class="form-control select2" ${readValue}>
    <c:if test="${not empty selectDesc}"><option value="">${selectDesc}</option> </c:if>
    <c:forEach items="${sysbookList}" var="sb" >
        <option value="${sb.listCode}" 
        	<c:if test="${sb.listCode==selectValue}">selected="selected" </c:if>>
        	${sb.listName}
        </option>
    </c:forEach>
</select>