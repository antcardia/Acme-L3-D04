<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>




<acme:button code="company.practicum.button.create" action="/company/practicum/create"/>

<br/><br/>

<acme:list>
	<acme:list-column code="company.practicum.list.label.code" path="code" width="10%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title" width="10%"/>
	<acme:list-column code="company.practicum.list.label.abstract$" path="abstract$" width="80%"/>	
</acme:list>
