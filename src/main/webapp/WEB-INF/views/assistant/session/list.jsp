<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.session.list.label.title" path="title" width="70%"/>
	<acme:list-column code="assistant.session.list.label.sessionType" path="sessionType" width="20%"/>		
</acme:list>

<jstl:choose>	 
		<jstl:when test="${_command == 'list'}">
			<acme:button code="assistant.tutorial.list.button.create" action="/assistant/session/create?masterId=${masterId}"/>
		</jstl:when>		
</jstl:choose>

