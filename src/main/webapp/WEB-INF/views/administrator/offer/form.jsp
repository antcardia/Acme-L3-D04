
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.offer.form.label.heading" path="heading"/>	
	<acme:input-moment code="administrator.offer.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textarea code="administrator.offer.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="administrator.offer.form.label.startDay" path="startDay"/>
	<acme:input-moment code="administrator.offer.form.label.lastDay" path="lastDay"/>
	<acme:input-money code="administrator.offer.form.label.price" path="price"/>
	<acme:input-url code="administrator.offer.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.offer.form.button.update" action="/administrator/offer/update"/>
			<acme:submit code="administrator.offer.form.button.delete" action="/administrator/offer/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.offer.form.button.create" action="/administrator/offer/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
