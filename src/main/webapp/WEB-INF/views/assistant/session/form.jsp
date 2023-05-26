<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.session.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.session.form.label.summary" path="summary"/>
	<acme:input-select choices="${sessionTypes}" code="assistant.session.form.label.sessionType" path="sessionType"/>	
	<acme:input-moment code = "assistant.session.form.label.start" path="start"/>
	<acme:input-moment code="assistant.session.form.label.end" path="end"/>
	<acme:input-url code="assistant.session.form.label.furtherInformation" path="furtherInformation"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && isPublished}">
			<acme:submit code="assistant.session.form.button.update" action="/assistant/session/update?id=${id}"/>
			<acme:submit code="assistant.session.form.button.delete" action="/assistant/session/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.session.form.button.create" action="/assistant/session/create?masterId=${masterId}"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>
