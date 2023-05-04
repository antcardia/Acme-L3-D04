<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message
		code="administrator.dashboard.form.title.general-indicators" />
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.principals" /></th>
		<td>
			<jstl:forEach items="${totalNumberOfPrincipalsByRole}" var="principal">
				<jstl:out value="${principal.key}"></jstl:out>
				<jstl:out value="${principal.value}"></jstl:out>
			</jstl:forEach>
		</td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.peepsRatio" /></th>

		<td><jstl:out value="${peepsWithEmailAddressAndLinkRatio}">
			</jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.criticalBulletinRatio" /></th>

		<td><jstl:out value="${criticalBulletinsRatio}">
			</jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.nonCriticalBulletinRatio" /></th>

		<td><jstl:out value="${nonCriticalBulletinsRatio}">
			</jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.avgBudget" /></th>
		<td><jstl:forEach items="${averageBudgetByCurrency}"
				var="avgBudget">
				<jstl:out value="${avgBudget.key}"></jstl:out>
				<jstl:out value="${avgBudget.value}"></jstl:out>
			</jstl:forEach></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.devBudget" /></th>
		<td><jstl:forEach items="${budgetDeviationByCurrency}"
				var="devBudget">
				<jstl:out value="${devBudget.key}"></jstl:out>
				<jstl:out value="${devBudget.value}"></jstl:out>
			</jstl:forEach></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.minBudget" /></th>
		<td><jstl:forEach items="${minBudgetByCurrency}" var="minBudget">
				<jstl:out value="${minBudget.key}"></jstl:out>
				<jstl:out value="${minBudget.value}"></jstl:out>
			</jstl:forEach></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.maxBudget" /></th>
		<td><jstl:forEach items="${maxBudgetByCurrency}" var="maxBudget">
				<jstl:out value="${maxBudget.key}"></jstl:out>
				<jstl:out value="${maxBudget.value}"></jstl:out>
			</jstl:forEach></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.avgNotes" /></th>

		<td><jstl:out value="${averageNotesPosted}">
			</jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.minNotes" /></th>

		<td><jstl:out value="${minNotesPosted}">
			</jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.maxNotes" /></th>

		<td><jstl:out value="${maxNotesPosted}">
			</jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.devNotes" /></th>

		<td><jstl:out value="${notesPostedDeviation}">
			</jstl:out></td>


	</tr>
</table>
<acme:return />