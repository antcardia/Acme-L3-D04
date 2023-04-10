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

package acme.features.lecturer.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.forms.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

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
		final LecturerDashboard dashboard = new LecturerDashboard();
		int userAccountId;
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		final Lecturer lecturer = this.repository.findOneLecturerByUserAccountId(userAccountId);
		final double avgLearningTimeLectures = this.repository.averageLearningTimeOfLectures(lecturer).orElse(0.0);
		final double devLearningTimeLectures = this.repository.deviationLearningTimeOfLectures(lecturer).orElse(0.0);
		final double minLearningTimeLectures = this.repository.minimumLearningTimeOfLectures(lecturer).orElse(0.0);
		final double maxLearningTimeLectures = this.repository.maximumLearningTimeOfLectures(lecturer).orElse(0.0);

		dashboard.setAverageLearningTimeOfLectures(avgLearningTimeLectures);
		dashboard.setDeviationLearningTimeOfLectures(devLearningTimeLectures);
		dashboard.setMinimumLearningTimeOfLectures(minLearningTimeLectures);
		dashboard.setMaximumLearningTimeOfLectures(maxLearningTimeLectures);

		final Collection<Double> courseEstimatedLearningTime = this.repository.findManyEstimatedLearningTimeByCourse(lecturer);
		dashboard.averageCalc(courseEstimatedLearningTime);
		dashboard.deviationCalc(courseEstimatedLearningTime);
		dashboard.minimumCalc(courseEstimatedLearningTime);
		dashboard.maximumCalc(courseEstimatedLearningTime);

		final Map<Nature, Integer> lecturesByType = new HashMap<Nature, Integer>();
		final Integer handsOnLectures = this.repository.totalLecturesByType(lecturer, Nature.HANDS_ON).orElse(0);
		final Integer theoreticalLectures = this.repository.totalLecturesByType(lecturer, Nature.THEORETICAL).orElse(0);
		lecturesByType.put(Nature.HANDS_ON, handsOnLectures);
		lecturesByType.put(Nature.THEORETICAL, theoreticalLectures);
		dashboard.setTotalLecturesByType(lecturesByType);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
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
