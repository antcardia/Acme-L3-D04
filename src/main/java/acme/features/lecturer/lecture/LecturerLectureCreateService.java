/*
 * EmployerDutyCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.courses.Course;
import acme.entities.courses.LectureCourse;
import acme.entities.lectures.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;

		object = new Lecture();
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "estimatedLearningTime", "body", "lectureType", "furtherInformation");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedLearningTime"))
			super.state(object.getEstimatedLearningTime() >= 0.01, "estimatedLearningTime", "lecturer.lecture.form.error.estimatedLearningTime");
		if (!super.getBuffer().getErrors().hasErrors("lectureType"))
			super.state(!object.getLectureType().equals(Nature.BALANCED), "lectureType", "lecturer.lecture.form.error.lectureType");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		this.repository.save(object);
		Course course;
		int masterId;
		final LectureCourse lc = new LectureCourse();

		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(masterId);
		lc.setLecture(object);
		lc.setCourse(course);
		this.repository.save(lc);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "estimatedLearningTime", "body", "draftMode", "lectureType", "furtherInformation");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));

		final SelectChoices choices = SelectChoices.from(Nature.class, object.getLectureType());
		tuple.put("lectureType", choices.getSelected().getKey());
		tuple.put("lecturesType", choices);
		super.getResponse().setData(tuple);
	}

}
