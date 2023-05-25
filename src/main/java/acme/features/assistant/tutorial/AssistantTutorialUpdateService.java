
package acme.features.assistant.tutorial;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.system.SystemConfiguration;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AssistantTutorialUpdateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		final boolean status;
		final Assistant assistant;
		int assistantId;
		Tutorial tutorial;
		int id;

		id = super.getRequest().getData("id", int.class);

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		tutorial = this.repository.findOneTutorialById(id);
		assistant = tutorial.getAssistant();

		status = super.getRequest().getPrincipal().hasRole(assistant) && assistant == this.repository.findOneAssistantById(assistantId);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
		Course course;
		final int courseId = super.getRequest().getData("course", int.class);

		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "summary", "goals", "estimatedTime", "draftMode");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "assistant.tutorial.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("summary")) {
			final String summary = object.getSummary();
			super.state(!antiSpam.isSpam(summary), "summary", "assistant.tutorial.form.error.spamSummary");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;
			final Tutorial tutorial = this.repository.findOneTutorialById(object.getId());
			existing = this.repository.findOneTutorialByCode(object.getCode());
			super.state(existing == null || tutorial.equals(existing), "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("estimatedTime"))
			super.state(object.getEstimatedTime() > 0 && object.getEstimatedTime() < 1000000, "estimatedTime", "assistant.tutorial.form.error.outOfRangeEstimatedTime");

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		Collection<Course> courses;
		String assistant;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses().stream().filter(x -> !x.isDraftMode()).collect(Collectors.toList());
		choices = SelectChoices.from(courses, "code", object.getCourse());
		assistant = object.getAssistant().getUserAccount().getUsername();

		tuple = super.unbind(object, "code", "title", "summary", "goals", "estimatedTime", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("assistant", assistant);

		super.getResponse().setData(tuple);
	}
}
