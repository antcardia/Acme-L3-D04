
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorial.form.label.summary" path="summary"/>	
	<acme:input-checkbox code = "assistant.tutorial.form.label.draftMode" path="draftMode" readonly="true"/>
	<acme:input-money code="assistant.tutorial.form.label.estimatedTime" path="estimatedTime"/>
	<acme:input-textbox code="assistant.tutorial.form.label.goals" path="goals"/>

	<acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${courses}"/>
	
	
<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="assistant.session.form.button.sessions" action="/assistant/session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode }">
			<acme:button code="assistant.session.form.button.sessions" action="/assistant/session/list?masterId=${id}"/>
			<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
			<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
		</jstl:when>
				
	</jstl:choose>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode  && status}">
			<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>
