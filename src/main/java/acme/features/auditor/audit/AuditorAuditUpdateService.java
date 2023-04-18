
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(auditId);
		status = audit != null && audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("code")) {

			final boolean existing = this.repository.existsAuditWithCode(object.getCode(), object.getId());
			super.state(!existing, "code", "auditor.audit.error.code.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(!object.getCourse().isDraftMode(), "course", "auditor.audit.error.course.draft");

		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(!object.getCourse().isDraftMode(), "course", "auditor.audit.error.course.draft");

		if (!super.getBuffer().getErrors().hasErrors("conclusion")) {
			final String conclusion = object.getConclusion();
			super.state(!antiSpam.isSpam(conclusion), "conclusion", "auditor.audit.form.error.spamconclusion");
		}
		if (!super.getBuffer().getErrors().hasErrors("strongPoints")) {
			final String strongPoints = object.getStrongPoints();
			super.state(!antiSpam.isSpam(strongPoints), "strongPoints", "auditor.audit.form.error.spamstrongpoints");
		}
		if (!super.getBuffer().getErrors().hasErrors("weakPoints")) {
			final String weakPoints = object.getWeakPoints();
			super.state(!antiSpam.isSpam(weakPoints), "weakPoints", "auditor.audit.form.error.spamweakpoints");
		}
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllPublishedCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
