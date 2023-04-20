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
	<jstl:if test="${status}">
		<acme:input-select code="assistant.session.form.label.tutorial" path="tutorial" choices="${tutorialOptions}"/>
	</jstl:if>
	<jstl:if test="${!status && status != null}">
		<acme:input-textbox code="assistant.session.form.label.tutorial" path="tutorial"/>
	</jstl:if>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && status && isPublished}">
			<acme:submit code="assistant.session.form.button.update" action="/assistant/session/update"/>
			<acme:submit code="assistant.session.form.button.delete" action="/assistant/session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="assistant.session.form.label.tutorial" path="tutorial" choices="${tutorialOptions}"/>
			<acme:submit code="assistant.session.form.button.create" action="/assistant/session/create"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>
