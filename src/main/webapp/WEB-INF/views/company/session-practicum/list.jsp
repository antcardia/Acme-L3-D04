<%--
- list.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/" %>

<acme:button code="company.session-practicum.button.create" action="/company/session-practicum/create?practicumId=${practicumId}" />    

<acme:list>
    <acme:list-column code="company.session-practicum.list.label.title" path="title" width="50%"/>
    <acme:list-column code="company.session-practicum.list.label.startTime" path="startTime" width="5%"/>
    <acme:list-column code="company.session-practicum.list.label.finishTime" path="finishTime" width="5%"/>
    <acme:list-column code="company.session-practicum.list.label.additional" path="additional" width="5%"/>
    <acme:list-column code="company.session-practicum.list.label.draftMode" path="draftMode" width="5%"/>
</acme:list>
