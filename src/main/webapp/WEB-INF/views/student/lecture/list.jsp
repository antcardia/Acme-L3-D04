<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.lecture.list.label.title" path="title" width="70%"/>
	<acme:list-column code="student.lecture.list.label.abstract$" path="abstract$" width="30%"/>
</acme:list>


