
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.tutorial.list.label.code" path="code" width="30%"/>
	<acme:list-column code="authenticated.tutorial.list.label.title" path="title" width="35%"/>
	<acme:list-column code="authenticated.tutorial.list.label.course" path="courseTitle" width="35%"/>	
</acme:list>


