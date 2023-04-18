
package acme.features.auditor.audit;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {
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
		Audit audit;

		audit = this.repository.findAuditById(super.getRequest().getData("id", int.class));

		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

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
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		final List<Mark> marks = this.repository.findAllReleasedMarksByAuditId(object.getId());

		final List<Course> courses = this.repository.findAllPublishedCourses();
		final SelectChoices choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("courses", choices);
		if (marks != null && !marks.isEmpty())
			tuple.put("marks", marks.stream().map(Mark::toString).collect(Collectors.joining(", ", "[ ", " ]")));
		else
			tuple.put("marks", "N/A");

		super.getResponse().setData(tuple);
	}
}
