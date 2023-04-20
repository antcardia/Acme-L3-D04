
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.offer.form.label.heading" path="heading"/>	
	<acme:input-moment code="authenticated.offer.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textarea code="authenticated.offer.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="authenticated.offer.form.label.startDay" path="startDay"/>
	<acme:input-moment code="authenticated.offer.form.label.lastDay" path="lastDay"/>
	<acme:input-money code="authenticated.offer.form.label.price" path="price"/>
	<acme:input-url code="authenticated.offer.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="authenticated.offer.form.button.update" action="/authenticated/offer/update"/>
			<acme:submit code="authenticated.offer.form.button.delete" action="/authenticated/offer/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.offer.form.button.create" action="/authenticated/offer/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>