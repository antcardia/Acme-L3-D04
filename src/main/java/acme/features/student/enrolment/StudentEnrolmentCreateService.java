/*
 * AuthenticatedProviderCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import antiSpamFilter.AntiSpamFilter;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Enrolment object;
		object = new Enrolment();
		object.setDraftMode(true);
		final Student student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "code", "motivation", "goals", "workTime", "draftMode");
		object.setCourse(course);

	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;

			existing = this.repository.findOneEnrolmentByCode(object.getCode());
			super.state(existing == null, "code", "student.enrolment.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("workTime"))
			super.state(object.getWorkTime() > 0, "workTime", "student.enrolment.form.error.negative-workTime");

		if (!super.getBuffer().getErrors().hasErrors("motivation")) {
			final String motivation = object.getMotivation();
			super.state(!antiSpam.isSpam(motivation), "motivation", "student.enrolment.form.error.spamTitle");
		}
		if (!super.getBuffer().getErrors().hasErrors("goals")) {
			final String goals = object.getGoals();
			super.state(!antiSpam.isSpam(goals), "goals", "student.enrolment.form.error.spamTitle2");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "draftMode");
		final Student student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());

		tuple.put("student", student.getUserAccount().getUsername());
		final SelectChoices choicesE = SelectChoices.from(this.repository.findAllCourse(), "code", object.getCourse());
		tuple.put("course", choicesE.getSelected().getKey());
		tuple.put("courseSelect", choicesE);
		super.getResponse().setData(tuple);
	}

}
