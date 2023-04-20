
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.course.form.label.code" path="code"/>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>
	<acme:input-textarea code="student.course.form.label.abstract$" path="abstract$"/>
	<acme:input-textbox code="student.course.form.label.draftMode" path="draftMode"/>
	<acme:input-money code="student.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox code="student.course.form.label.furtherInformation" path="furtherInformation"/>
	<acme:input-textbox code="student.course.form.label.lecturer" path="lecturer" readonly="true"/>
	<acme:button code="student.course.form.button.lecture" action="/student/lecture/list?id=${id}"/>
</acme:form>
