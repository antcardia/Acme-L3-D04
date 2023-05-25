
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

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
		status = course != null && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyLecturesByCourseId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "estimatedLearningTime", "lectureType");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Lecture> objects) {
		assert objects != null;

		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);

		super.getResponse().setGlobal("masterId", masterId);
	}
}
