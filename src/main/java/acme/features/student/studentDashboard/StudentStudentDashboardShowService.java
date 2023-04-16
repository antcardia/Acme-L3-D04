/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.studentDashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.StudentDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentStudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentStudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final StudentDashboard dashboard = new StudentDashboard();
		int userAccountId;
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		final Student student = this.repository.findOneStudentByUserAccountId(userAccountId);
		final double avgLearningTimeLectures = this.repository.averageLearningTimeOfStudent(student).orElse(0.0);
		final double devLearningTimeLectures = this.repository.deviationLearningTimeOfStudent(student).orElse(0.0);
		final double minLearningTimeLectures = this.repository.minimumLearningTimeOfStudent(student).orElse(0.0);
		final double maxLearningTimeLectures = this.repository.maximumLearningTimeOfStudent(student).orElse(0.0);

		dashboard.setAverageLearningTimeOfStudent(avgLearningTimeStudent);
		dashboard.setDeviationLearningTimeOfStudent(devLearningTimeStudent);
		dashboard.setMinimumLearningTimeOfStudent(minLearningTimeStudent);
		dashboard.setMaximumLearningTimeOfStudent(maxLearningTimeStudent);

		final Collection<Double> courseEstimatedLearningTime = this.repository.findManyEstimatedLearningTimeByCourse(lecturer);
		dashboard.averageCalc(courseEstimatedLearningTime);
		dashboard.deviationCalc(courseEstimatedLearningTime);
		dashboard.minimumCalc(courseEstimatedLearningTime);
		dashboard.maximumCalc(courseEstimatedLearningTime);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"totalLecturesByType", "averageLearningTimeOfLectures", // 
			"deviationLearningTimeOfLectures", "minimumLearningTimeOfLectures", //
			"maximumLearningTimeOfLectures", "averageLearningTimeOfCourses", // 
			"deviationLearningTimeOfCourses", "minimumLearningTimeOfCourses", // 
			"maximumLearningTimeOfCourses");

		super.getResponse().setData(tuple);
	}

}
