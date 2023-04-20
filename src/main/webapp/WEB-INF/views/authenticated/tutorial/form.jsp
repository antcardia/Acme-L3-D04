
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.summary" path="summary"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.goals" path="goals"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.estimatedTime" path="estimatedTime"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.course" path="courseTitle"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant" path="assistantName"/>
</acme:form>
