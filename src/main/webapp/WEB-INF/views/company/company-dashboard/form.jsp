<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<table class="table table-sm">

	<tr>
	<th scope="row">
			<acme:message code="company.dashboard.label.average-time-of-practicumSessions"/>
		</th>
		<td>
			<acme:print value="${averageSessionPracticumTime}"/>
		</td>
	</tr>
	<tr>
	<th scope="row">
			<acme:message code="company.dashboard.label.deviation-time-of-practicumSessions"/>
		</th>
		<td>
			<acme:print value="${deviationSessionPracticumTime}"/>
		</td>
	</tr>
	<tr>
	<th scope="row">
			<acme:message code="company.dashboard.label.minimum-time-of-practicumSessions"/>
		</th>
		<td>
			<acme:print value="${minimumSessionPracticumTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.maximum-time-of-practicumSessions"/>
		</th>
		<td>
			<acme:print value="${maximumSessionPracticumTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.average-time-of-practicums"/>
		</th>
		<td>
			<acme:print value="${averagePracticaLength}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.deviation-time-of-practicums"/>
		</th>
		<td>
			<acme:print value="${deviationPracticaLength}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.minimum-time-of-practicums"/>
		</th>
		<td>
			<acme:print value="${minimumPracticaLength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.maximum-time-of-practicums"/>
		</th>
		<td>
			<acme:print value="${maximumPracticaLength}"/>
		</td>
	</tr>		
</table>