
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


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
		final Collection<Activity> objects;
		final Enrolment enrolment = this.repository.findEnrolmentById(super.getRequest().getData("masterId", int.class));
		objects = this.repository.findActivitiesByEnrolmentId(enrolment.getId());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "tittle", "workbookName");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Activity> objects) {
		assert objects != null;

		final Integer enrolmentId = super.getRequest().getData("masterId", int.class);
		super.getResponse().setGlobal("masterId", enrolmentId);
	}

}
