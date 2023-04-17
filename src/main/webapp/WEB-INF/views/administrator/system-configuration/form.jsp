<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.systemConfiguration.form.label.systemCurrency" path="systemCurrency"/>	
	<acme:input-textbox code="administrator.systemConfiguration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:input-double code="administrator.systemConfiguration.form.label.threshold" path="threshold"/>
	<acme:input-textarea code="administrator.systemConfiguration.form.label.spamWords" path="spamWords"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update')}">
			<acme:submit code="administrator.systemConfiguration.form.button.update" action="/administrator/system-configuration/update"/>
		</jstl:when>	
	</jstl:choose>
</acme:form>
