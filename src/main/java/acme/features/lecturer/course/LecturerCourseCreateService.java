
package acme.features.lecturer.course;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import antiSpamFilter.AntiSpamFilter;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Course object;
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abstract$", "retailPrice", "furtherInformation");
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
			final Course existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(currencies.contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.course.form.error.notAcceptedCurrency");

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() >= 0 && object.getRetailPrice().getAmount() < 1000000, "retailPrice", "lecturer.course.form.error.outOfRangeRetailPrice");
		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(object.getFurtherInformation().length() < 255, "furtherInformation", "lecturer.course.form.error.outOfRangeLink");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstract$", "draftMode", "retailPrice", "furtherInformation");

		super.getResponse().setData(tuple);
	}
}
