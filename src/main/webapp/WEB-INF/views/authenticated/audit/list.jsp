<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="audit.label.code" path="code" width="20%"/>
	<acme:list-column code="audit.label.conclusion" path="conclusion" width="20%"/>
	<acme:list-column code="audit.label.auditorName" path="auditor" width="20%"/>
</acme:list>