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

<h2>
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalLecturesByType"/>
		</th>
		<td>
			<acme:print value="${totalLecturesByType}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageLearningTimeOfLectures"/>
		</th>
		<td>
			<acme:print value="${averageLearningTimeOfLectures}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationLearningTimeOfLectures"/>
		</th>
		<td>
			<acme:print value="${deviationLearningTimeOfLectures}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimumLearningTimeOfLectures"/>
		</th>
		<td>
			<acme:print value="${minimumLearningTimeOfLectures}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximumLearningTimeOfLectures"/>
		</th>
		<td>
			<acme:print value="${maximumLearningTimeOfLectures}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageLearningTimeOfCourses"/>
		</th>
		<td>
			<acme:print value="${averageLearningTimeOfCourses}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationLearningTimeOfCourses"/>
		</th>
		<td>
			<acme:print value="${deviationLearningTimeOfCourses}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimumLearningTimeOfCourses"/>
		</th>
		<td>
			<acme:print value="${minimumLearningTimeOfCourses}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximumLearningTimeOfCourses"/>
		</th>
		<td>
			<acme:print value="${maximumLearningTimeOfCourses}"/>
		</td>
	</tr>				
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.application-statuses"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"average", "deviation", "minimum", "maximum"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${averageLearningTimeOfCourses}"/>, 
						<jstl:out value="${deviationLearningTimeOfCourses}"/>, 
						<jstl:out value="${minimumLearningTimeOfCourses}"/>
						<jstl:out value="${maximumLearningTimeOfCourses}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

