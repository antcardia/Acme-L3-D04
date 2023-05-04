
package acme.features.company.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

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
		Practicum practicum;

		practicum = this.repository.findPracticumById(super.getRequest().getData("id", int.class));

		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(practicumId);
		super.getBuffer().setData(object);
	}
	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;

		super.bind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime", "draftMode");
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		object.setCourse(course);

	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;

			existing = this.repository.findPracticumByCode(object.getCode());
			super.state(existing == null || existing.getCode().equals(object.getCode()), "code", "company.practicum.error.code.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode() == true, "draftMode", "company.practicum.error.draftMode.negative");

	}
	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(this.repository.findAllCourses(), "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
