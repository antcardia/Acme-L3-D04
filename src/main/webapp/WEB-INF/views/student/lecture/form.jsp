<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="student.lecture.form.label.abstract$" path="abstract$"/>
	<acme:input-double code="student.lecture.form.label.estimatedLearningTime" path="estimatedLearningTime"/>
	<acme:input-textarea code="student.lecture.form.label.body" path="body"/>
	<acme:input-textarea code="student.lecture.form.label.draftMode" path="draftMode"/>
	<acme:input-textbox code="student.lecture.form.label.lectureType" path="lectureType"/>
	<acme:input-textbox code="student.lecture.form.label.furtherInformation" path="furtherInformation"/>
</acme:form>
