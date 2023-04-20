<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.duty.list.label.title" path="title" width="80%"/>	
	<acme:list-column code="any.duty.list.label.workLoad" path="workLoad" width="20%"/>
</acme:list>
