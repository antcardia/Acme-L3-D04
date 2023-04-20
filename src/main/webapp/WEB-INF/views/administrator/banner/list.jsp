
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.startPeriod" path="startPeriod" width="70%"/>
	<acme:list-column code="administrator.banner.list.label.endPeriod" path="endPeriod" width="30%"/>
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>


