
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.activity.form.label.tittle" path="tittle" width="70%"/>
	<acme:list-column code="student.activity.form.label.workbookName" path="workbookName" width="30%"/>
</acme:list>

<acme:button code="student.activity.form.button.create" action="/student/activity/create?masterId=${masterId}"/>
