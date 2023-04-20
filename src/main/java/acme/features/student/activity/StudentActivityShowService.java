
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.enrolment.Activity;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "tittle", "abstract$", "workbookName", "startTime", "finishTime", "link");
		final SelectChoices choices = SelectChoices.from(Nature.class, object.getAtype());
		tuple.put("atype", choices.getSelected().getKey());
		tuple.put("activityType", choices);
		final SelectChoices choicesE = SelectChoices.from(this.repository.findAllEnrolmentByStudentId(super.getRequest().getPrincipal().getActiveRoleId()), "code", object.getEnrolment());
		tuple.put("enrolment", choicesE.getSelected().getKey());
		tuple.put("enrolmentSelect", choicesE);

		super.getResponse().setData(tuple);
	}
}
