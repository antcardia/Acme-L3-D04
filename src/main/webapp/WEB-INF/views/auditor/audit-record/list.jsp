<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="auditor.auditrecord.label.subject" path="subject" width="20%"/>
	<acme:list-column code="auditor.auditrecord.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.auditrecord.label.hours" path="hours" width="20%"/>
	<acme:list-column code="auditor.auditrecord.label.draft" path="draft" width="20%"/>
	<acme:list-column code="auditor.auditrecord.label.correction" path="correction" width="20%"/>
</acme:list>
<%-- <jstl:if test="${draftmode==true}">
	<acme:button code="auditor.audit.form.button.create" action="/auditor/audit-record/create?auditId=${id}"/>
</jstl:if>
<jstl:if test="${draftmode==false}">
	<acme:button code="auditor.audit.form.button.correct" action="/auditor/audit-record/correct?auditId=${id}"/>
</jstl:if> --%>