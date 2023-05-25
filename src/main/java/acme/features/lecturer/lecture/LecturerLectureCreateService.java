
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.lectures.Lecture;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import antiSpamFilter.AntiSpamFilter;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Lecture object;

		object = new Lecture();
		object.setDraftMode(true);
		final Lecturer lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setLecturer(lecturer);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "estimatedLearningTime", "body", "lectureType", "furtherInformation");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "lecturer.lecture.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("abstract$")) {
			final String summary = object.getAbstract$();
			super.state(!antiSpam.isSpam(summary), "abstract$", "lecturer.lecture.form.error.spamAbstract");
		}

		if (!super.getBuffer().getErrors().hasErrors("body")) {
			final String body = object.getBody();
			super.state(!antiSpam.isSpam(body), "body", "lecturer.lecture.form.error.spamBody");
		}

		if (!super.getBuffer().getErrors().hasErrors("estimatedLearningTime"))
			super.state(object.getEstimatedLearningTime() > 0, "estimatedLearningTime", "lecturer.lecture.form.error.estimatedLearningTime");

		if (!super.getBuffer().getErrors().hasErrors("lectureType"))
			super.state(!object.getLectureType().equals(Nature.BALANCED), "lectureType", "lecturer.lecture.form.error.lectureType");

		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(object.getFurtherInformation().length() < 255, "furtherInformation", "lecturer.lecture.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "estimatedLearningTime", "body", "lecturer", "draftMode", "lectureType", "furtherInformation");

		final SelectChoices choices = SelectChoices.from(Nature.class, object.getLectureType());
		tuple.put("lectureType", choices.getSelected().getKey());
		tuple.put("lecturesType", choices);
		super.getResponse().setData(tuple);
	}

}
