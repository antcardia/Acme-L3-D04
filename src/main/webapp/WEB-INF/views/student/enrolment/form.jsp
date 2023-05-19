
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textarea code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-double code="student.enrolment.form.label.workTime" path="workTime"/>
	<acme:input-checkbox code="student.enrolment.form.label.draftMode" path="draftMode" readonly="true"/>
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courseSelect}"/>
	<acme:input-textbox code="student.enrolment.form.label.holderName" path="holderName" readonly="true"/>
	<acme:input-textbox code="student.enrolment.form.label.creditCardFourLowNibble" path="creditCardFourLowNibble" readonly="true"/> 
	<jstl:choose>
		<jstl:when test="${draftMode == false}">
			<acme:button code="student.activity.form.button.activities" action="/student/activity/list?masterId=${id}"/>							
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:button code="student.enrolment.form.button.payment" action="/student/payment/create?masterId=${id}"/>	
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>