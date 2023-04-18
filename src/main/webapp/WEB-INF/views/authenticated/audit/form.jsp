<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="audit.label.code" path="code"/>
	<acme:input-textbox code="audit.label.auditorName" path="auditorName"/>
	<acme:input-textbox code="audit.label.courseName" path="courseName"/>
	<acme:input-textbox code="audit.label.courseLecturer" path="courseLecturer"/>
	<acme:input-textbox code="audit.label.strongPoints" path="strongPoints"/>
	<acme:input-textbox code="audit.label.weakPoints" path="weakPoints"/>
	<acme:input-textbox code="audit.label.conclusion" path="conclusion"/>
	<acme:input-textbox code="audit.label.marks" path="marks"/>

</acme:form>