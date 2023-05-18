
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.payment.form.label.holderName" path="holderName"/>
	<acme:input-textbox code="student.payment.form.label.creditCard" path="creditCard"/>
	<acme:input-textarea code="student.payment.form.label.expirationDate" path="expirationDate"/>
	<acme:input-double code="student.payment.form.label.securityCode" path="securityCode"/>
	<acme:submit code="student.payment.form.button.publish" action="/student/payment/create?masterId=${masterId}"/>	
</acme:form>