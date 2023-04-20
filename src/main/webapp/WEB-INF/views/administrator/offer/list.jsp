
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.offer.list.label.heading" path="heading" width="70%"/>
	<acme:list-column code="administrator.offer.list.label.price" path="price" width="30%"/>
</acme:list>

<acme:button code="administrator.offer.list.button.create" action="/administrator/offer/create"/>
