
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.activity.form.label.tittle" path="tittle"/>	
	<acme:input-textarea code="student.activity.form.label.abstract$" path="abstract$"/>
	<acme:input-textbox code="student.activity.form.label.workbookName" path="workbookName"/>
	<acme:input-select code="student.activity.form.label.atype" path="atype" choices="${activityType}"/>
	<acme:input-moment code="student.activity.form.label.startTime" path="startTime"/>
	<acme:input-moment code="student.activity.form.label.finishTime" path="finishTime"/>
	<acme:input-url code="student.activity.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update?id=${id}"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activity.form.button.create" action="/student/activity/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
