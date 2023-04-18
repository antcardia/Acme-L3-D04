<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="auditor.audit.label.code" path="code" width="20%"/>
	<acme:list-column code="auditor.audit.label.conclusion" path="conclusion" width="20%"/>
	<acme:list-column code="auditor.audit.label.courseCode" path="courseCode" width="20%"/>
	<acme:list-column code="auditor.audit.label.course" path="course" width="20%"/>
	<acme:list-column code="auditor.audit.label.draft" path="draft" width="20%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
</jstl:if>