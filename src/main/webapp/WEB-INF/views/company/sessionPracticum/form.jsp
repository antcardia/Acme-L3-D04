<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textarea code="company.session-practicum.form.label.title" path="title"/>
	<acme:input-textarea code="company.session-practicum.form.label.abstract" path="abstract$"/>
	<acme:input-moment code="company.session-practicum.form.label.start-time" path="startTime"/>
	<acme:input-moment code="company.session-practicum.form.label.finish-time" path="finishTime"/>
	<acme:input-url code="company.session-practicum.form.label.further-information" path="furtherInformation"/>
	
	<jstl:if test="${confirmation}">
		<acme:input-checkbox code="company.addendum-session.form.label.accept" path="accept"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.session-practicum.form.button.update" action="/company/session-practicum/update"/>
			<acme:submit code="company.session-practicum.form.button.delete" action="/company/session-practicum/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.session-practicum.form.button.create" action="/company/session-practicum/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-addendum'}">
			<acme:submit code="company.session-practicum.form.button.create" action="/company/session-practicum/create-addendum?masterId=${masterId}"/>
		</jstl:when>			
	</jstl:choose>		
</acme:form>


