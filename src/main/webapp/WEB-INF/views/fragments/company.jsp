
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h1><acme:message code="master.company.title"/></h1>

<p><acme:message code="master.company.text"/></p>

<address>
  <strong><acme:message code="master.company.name"/></strong> <br/>
  <span class="fas fa-map-marker"> &nbsp; </span><acme:message code="master.company.address"/> <br/>
  <span class="fa fa-phone"></span> &nbsp; <acme:message code="master.company.phone"/><br/>
  <span class="fa fa-at"></span> &nbsp; <a href="mailto:<acme:message code="master.company.email"/>"><acme:message code="master.company.email"/></a> <br/>
</address>

