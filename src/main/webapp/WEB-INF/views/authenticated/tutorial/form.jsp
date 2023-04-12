<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not tutorial any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>	
	<acme:input-moment code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.summary" path="summary"/>
	<acme:input-moment code="authenticated.tutorial.form.label.goal" path="goal"/>
	<acme:input-moment code="authenticated.tutorial.form.label.estimatedTime" path="estimatedTime"/>
	<acme:input-money code="authenticated.tutorial.form.label.course" path="course.title"/>
	<acme:input-url code="authenticated.tutorial.form.label.assistant" path="assistant.id"/>
</acme:form>
