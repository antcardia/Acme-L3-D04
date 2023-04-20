
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.offer.list.label.heading" path="heading" width="70%"/>
	<acme:list-column code="authenticated.offer.list.label.price" path="price" width="30%"/>
</acme:list>

<acme:button code="authenticated.offer.list.button.create" action="/authenticated/offer/create"/>

