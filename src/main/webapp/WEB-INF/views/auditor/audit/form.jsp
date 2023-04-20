<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	
	<acme:input-textbox code="auditor.audit.label.code" path="code"/>
	<acme:input-select code="auditor.audit.label.courseCode" path="course" choices="${courses}"/>
	<acme:input-textarea code="auditor.audit.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.label.weakPoints" path="weakPoints"/>
	<acme:input-textarea code="auditor.audit.label.conclusion" path="conclusion"/>
	<jstl:if test="${_command != 'create'}">
		<acme:input-textbox code="auditor.audit.label.marks" path="marks" readonly="true"/>
	</jstl:if>
	
	<acme:hidden-data path="draftMode"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
			<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			<acme:submit code="auditor.audit.form.button.publish" action="/auditor/audit/publish"/>
			<acme:button code="auditor.audit.form.button.addAuditrecord" action="/auditor/audit-record/create?auditId=${id}"/>
		</jstl:when>
		<jstl:when test="${(_command == 'show') && draftMode==false}">
			<acme:button code="auditor.audit.form.button.addCorrectionAuditRecord" action="/auditor/audit-record/correct?auditId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>		
	</jstl:choose>
	<jstl:if test="${_command != 'create'}">
			<acme:button code="auditor.audit.form.button.auditrecords" action="/auditor/audit-record/list?auditId=${id}"/>
	</jstl:if>
	
</acme:form>