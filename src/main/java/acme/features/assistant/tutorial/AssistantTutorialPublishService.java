
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.system.SystemConfiguration;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AssistantTutorialPublishService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		int tutorialId;
		Tutorial tutorial;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = assistant == this.repository.findOneAssistantById(assistantId) && tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(assistant)
			&& !this.repository.findManySessionsByTutorialId(tutorial.getId()).isEmpty();

		super.getResponse().setAuthorised(status);
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

		super.bind(object, "code", "title", "summary", "goals", "estimatedTime", "draftMode");
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "lecturer.course.form.error.spamTitle");
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

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getEstimatedTime() > 0 && object.getEstimatedTime() < 1000000, "estimatedTime", "assistant.tutorial.form.error.outOfRangeEstimatedTime");

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		Tuple tuple;
		final String assistantName = object.getAssistant().getUserAccount().getUsername();
		final String courseTitle = object.getCourse().getTitle();
		boolean status;
		Collection<Session> sessions;

		sessions = this.repository.findManySessionsByTutorialId(object.getId());

		status = !(sessions == null || sessions.isEmpty());

		tuple = super.unbind(object, "code", "title", "summary", "goals", "estimatedTime", "draftMode");
		tuple.put("assistantName", assistantName);
		tuple.put("courseTitle", courseTitle);
		tuple.put("status", status);
		super.getResponse().setData(tuple);
	}

}
