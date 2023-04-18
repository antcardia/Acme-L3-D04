
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentListService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


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
		Enrolment enrolment;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(masterId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		List<Enrolment> objects;

		final Integer idStudent = super.getRequest().getPrincipal().getActiveRoleId();

		objects = this.repository.findAllEnrolmentByStudentId(idStudent);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation");

		super.getResponse().setData(tuple);
	}
}
