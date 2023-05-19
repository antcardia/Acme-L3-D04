
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
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

		boolean status;
		int masterId;
		Activity activity;
		Enrolment enrolment;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(masterId);
		enrolment = activity == null ? null : activity.getEnrolment();
		student = enrolment == null ? null : enrolment.getStudent();
		status = activity != null && !activity.getEnrolment().isDraftMode() && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
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

		super.getResponse().setData(tuple);
	}
}
