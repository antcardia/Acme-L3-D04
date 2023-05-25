
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
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Enrolment enrolment;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(masterId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
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
		super.bind(object, "code", "motivation", "goals", "workTime", "draftMode", "creditCardFourLowNibble", "holderName");
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;
			final Enrolment enrolment = this.repository.findEnrolmentById(object.getId());
			existing = this.repository.findOneEnrolmentByCode(object.getCode());
			super.state(existing == null || enrolment.equals(existing), "code", "student.enrolment.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("motivation")) {
			final String motivation = object.getMotivation();
			super.state(!antiSpam.isSpam(motivation), "motivation", "student.enrolment.form.error.spamTitle");
		}
		if (!super.getBuffer().getErrors().hasErrors("goals")) {
			final String goals = object.getGoals();
			super.state(!antiSpam.isSpam(goals), "goals", "student.enrolment.form.error.spamTitle2");
		}
		if (!super.getBuffer().getErrors().hasErrors("workTime")) {
			final Double workTime = object.getWorkTime();
			super.state(workTime > 0, "workTime", "student.enrolment.form.error.workTime");
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

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "draftMode", "creditCardFourLowNibble", "holderName");
		final Student student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());

		tuple.put("student", student);
		final SelectChoices choicesE = SelectChoices.from(this.repository.findAllCourse(), "code", object.getCourse());
		tuple.put("course", choicesE.getSelected().getKey());
		tuple.put("courseSelect", choicesE);
		super.getResponse().setData(tuple);
	}
}
