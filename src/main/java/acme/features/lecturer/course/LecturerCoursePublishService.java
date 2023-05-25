
package acme.features.lecturer.course;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import antiSpamFilter.AntiSpamFilter;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		int courseId;
		Course course;
		Lecturer lecturer;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abstract$", "retailPrice", "furtherInformation", "courseNature");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final List<String> currencies = Arrays.asList(config.getAcceptedCurrencies().split(","));
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "lecturer.course.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("abstract$")) {
			final String summary = object.getAbstract$();
			super.state(!antiSpam.isSpam(summary), "abstract$", "lecturer.course.form.error.spamAbstract");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;
			final Course course = object.getCode().equals(null) ? null : this.repository.findOneCourseById(object.getId());
			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || course.equals(existing), "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(currencies.contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.course.form.error.notAcceptedCurrency");

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() >= 0 && object.getRetailPrice().getAmount() < 1000000, "retailPrice", "lecturer.course.form.error.outOfRangeRetailPrice");

		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(object.getFurtherInformation().length() < 255, "furtherInformation", "lecturer.course.form.error.outOfRangeLink");

		final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(object.getId());
		super.state(!lectures.isEmpty(), "*", "lecturer.course.form.error.lecture-not-found");
		if (!lectures.isEmpty()) {
			final boolean handsOnLecture = lectures.stream().anyMatch(x -> x.getLectureType().equals(Nature.HANDS_ON));
			super.state(handsOnLecture, "*", "lecturer.course.form.error.no-hands-on-lecture");
			final boolean publishedLectures = lectures.stream().allMatch(x -> x.isDraftMode() == false);
			super.state(publishedLectures, "*", "lecturer.course.form.error.no-published-lecture");
		}
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstract$", "draftMode", "retailPrice", "furtherInformation");
		final List<Lecture> lectures = this.repository.findManyLecturesByCourseId(object.getId()).stream().collect(Collectors.toList());
		final Nature nature = object.courseNature(lectures);
		tuple.put("courseNature", nature);
		super.getResponse().setData(tuple);
	}

}
