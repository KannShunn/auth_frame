<%@ tag language="java" pageEncoding="UTF-8"%><%@attribute name="title" type="java.lang.Boolean"%><%@attribute name="resizable" type="java.lang.Boolean"%><%@attribute name="width"%><%@attribute name="key" type="java.lang.Boolean"%><%@attribute name="frozen" type="java.lang.Boolean"%><%@attribute name="convertCode"%><%@attribute name="revertCode" type="java.lang.Boolean"%><%@attribute name="convertTextFiled"%><%@attribute name="convertValueFiled"%><%@attribute name="cellattr" %><%@attribute name="name" type="java.lang.Object" rtexprvalue="true"%><%@attribute name="header"%><%@attribute name="property"%><%@attribute name="dataType"%><%@attribute name="index"%><%@attribute name="align"%><%@attribute name="sorttype"%><%@attribute name="formatter"%><%@attribute name="unformat"%><%@attribute name="formatoptions"%><%@attribute name="cellEditoptions"%><%@attribute name="datefmt"%><%@attribute name="sortable" type="java.lang.Boolean"%><%@attribute name="editable" type="java.lang.Boolean"%><%@attribute name="edittype"%><%@attribute name="hidden" type="java.lang.Boolean"%><%@attribute name="fixed"%><%@attribute name="summaryType"%><%@attribute name="summaryTpl"%><%@attribute name="columnSortable" type="java.lang.Boolean"%><%@attribute name="cls"%><%@attribute name="editoptions"%><%@include file="TagUtil.jsp" %><%
String option = tagUtil.add("width", width)
	.add("key", key)
	.add("frozen", frozen)
	.add("title", title)
	.add("resizable", resizable)
	.add("convertCode", convertCode)
	.add("revertCode", revertCode)
	.add("convertTextFiled", convertTextFiled)
	.add("convertValueFiled", convertValueFiled)
	.add("cellattr", cellattr)
	.add("name", name)
	.add("columnSortable", columnSortable)
	.add("header", header)
	.add("property", property)
	.add("dataType", dataType)
	.add("index", index)
	.add("align", align)
	.add("sorttype", sorttype)
	.add("formatter", formatter)
	.add("unformat", unformat)
	.add("formatoptions", formatoptions, "options")
	.add("cellEditoptions", cellEditoptions)
	.add("sortable", sortable)
	.add("editable", editable)
	.add("edittype", edittype)
	.add("hidden", hidden)
	.add("fixed", fixed)
	.add("summaryType", summaryType)
	.add("summaryTpl", summaryTpl)
	.add("cls", cls)
	.add("controllerName", request.getAttribute("controllerName"))
	.add("editoptions", editoptions, "options").toString();
%><div data-options="<%=option %>"><jsp:doBody /></div>