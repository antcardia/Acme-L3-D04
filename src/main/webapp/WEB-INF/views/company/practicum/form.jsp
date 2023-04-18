<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>

	<acme:input-textbox code="company.practicum.list.label.code" path="code"/>
	<acme:input-textbox code="company.practicum.list.label.title" path="title"/>
	<acme:input-textarea code="company.practicum.list.label.abstract$" path="abstract$"/>
	<acme:input-textbox code="company.practicum.list.label.goals" path="goals"/>
	<acme:input-textbox code="company.practicum.list.label.estimatedTotalTime" path="estimatedTotalTime"/>	
	<acme:input-textbox code="company.practicum.list.label.draftMode" path="draftMode" readonly="true"/>
	<acme:input-select code="company.practicum.list.label.course" path="course" choices="${courses}"/>
	
	<acme:submit test="${_command == 'create'}" code="company.practicum.button.create" action="/company/practicum/create"/>

	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="company.practicum.button.update" action="/company/practicum/update"/>
		<acme:submit code="company.practicum.button.delete" action="/company/practicum/delete"/>		
		<acme:submit code="company.practicum.button.publish" action="/company/practicum/publish"/>		
	</jstl:if>
	
</acme:form>


