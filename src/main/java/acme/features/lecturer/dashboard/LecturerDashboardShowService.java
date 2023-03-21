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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		final LecturerDashboard dashboard;
		final Double averageLearningTimeOfLectures;

		averageLearningTimeOfLectures = this.repository.averageLearningTimeOfLectures();
		//		averageNumberOfApplicationsPerWorker = this.repository.averageNumberOfApplicationsPerWorker();
		//		averageNumberOfJobsPerEmployer = this.repository.averageNumberOfJobsPerEmployer();
		//		ratioOfPendingApplications = this.repository.ratioOfPendingApplications();
		//		ratioOfAcceptedApplications = this.repository.ratioOfAcceptedApplications();
		//		ratioOfRejectedApplications = this.repository.ratioOfRejectedApplications();

		dashboard = new LecturerDashboard();
		dashboard.setAverageLearningTimeOfLectures(averageLearningTimeOfLectures);
		//		dashboard.setAverageNumberOfApplicationsPerWorker(averageNumberOfApplicationsPerWorker);
		//		dashboard.setAverageNumberOfJobsPerEmployer(averageNumberOfJobsPerEmployer);
		//		dashboard.setRatioOfPendingApplications(ratioOfPendingApplications);
		//		dashboard.setRatioOfAcceptedApplications(ratioOfAcceptedApplications);
		//		dashboard.setRatioOfRejectedApplications(ratioOfRejectedApplications);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"averageLearningTimeOfLectures", "averageNumberOfApplicationsPerWorker", // 
			"avegageNumberOfApplicationsPerEmployer", "ratioOfPendingApplications", //
			"ratioOfRejectedApplications", "ratioOfAcceptedApplications");

		super.getResponse().setData(tuple);
	}

}
