
package acme.features.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.LectureCourse;
import acme.entities.lectures.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseCreateService extends AbstractService<Lecturer, LectureCourse> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("lectureId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureId;
		Lecture lecture;
		Lecturer lecturer;

		lectureId = super.getRequest().getData("lectureId", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = lecture != null && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final LectureCourse object = new LectureCourse();
		int lectureId;
		final Lecture lecture;
		lectureId = super.getRequest().getData("lectureId", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		object.setLecture(lecture);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final LectureCourse object) {
		assert object != null;
		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "id");
		object.setCourse(course);
	}

	@Override
	public void validate(final LectureCourse object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("lecture") && !super.getBuffer().getErrors().hasErrors("course")) {
			final Collection<Lecture> lectures = this.repository.findManyLecturesByMasterId(object.getCourse().getId());
			super.state(!lectures.contains(object.getLecture()), "course", "lecturer.lectureCourse.form.error.lecture");
		}
		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(object.getCourse().isDraftMode(), "course", "lecturer.lectureCourse.form.error.course");
	}

	@Override
	public void perform(final LectureCourse object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;
		Tuple tuple;
		int lectureId;
		Lecturer lecturer;
		Collection<Course> courses;
		Lecture lecture;

		tuple = super.unbind(object, "course", "lecture");
		lectureId = super.getRequest().getData("lectureId", int.class);
		tuple.put("lectureId", lectureId);

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		courses = this.repository.findManyCoursesByLecturer(lecturer);
		lecture = this.repository.findOneLectureById(lectureId);

		final SelectChoices choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("draftMode", lecture.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
