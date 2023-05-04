
package acme.features.assistant.session;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.system.SystemConfiguration;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AssistantSessionCreateService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

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
		final Session object;

		object = new Session();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;
		Tutorial tutorial;
		final int tutorialId = super.getRequest().getData("tutorial", int.class);
		Nature sessionType;
		sessionType = super.getRequest().getData("sessionType", Nature.class);

		tutorial = this.repository.findOneTutorialById(tutorialId);
		object.setTutorial(tutorial);
		object.setSessionType(sessionType);

		super.bind(object, "title", "summary", "sessionType", "start", "end", "furtherInformation");
	}

	@Override
	public void validate(final Session object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "assistant.session.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("summary")) {
			final String summary = object.getSummary();
			super.state(!antiSpam.isSpam(summary), "abstract$", "assistant.session.form.error.spamSummary");
		}

		if (!super.getBuffer().getErrors().hasErrors("start")) {
			final Date start = object.getStart();
			super.state(start.compareTo(object.getEnd()) <= 0, "start", "assistant.session.form.error.badDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("end")) {
			final Date start = object.getStart();
			super.state(start.compareTo(object.getEnd()) <= 0, "end", "assistant.session.form.error.badDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("sessionType")) {
			final Nature sessionType = object.getSessionType();
			super.state(sessionType != Nature.BALANCED, "sessionType", "assistant.session.form.error.badSessionType");
		}
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Session object) {
		Tuple tuple;
		SelectChoices natures;
		SelectChoices tutorialOptions;
		Collection<Tutorial> tutorials;
		final Assistant assistant;

		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());

		tutorials = this.repository.findAllTutorial().stream().filter(x -> x.getAssistant() == assistant).collect(Collectors.toList());

		tuple = super.unbind(object, "title", "summary", "sessionType", "start", "end", "furtherInformation");

		natures = SelectChoices.from(Nature.class, object.getSessionType());
		tuple.put("sessionTypes", natures);

		tutorialOptions = SelectChoices.from(tutorials, "code", object.getTutorial());
		tuple.put("tutorial", tutorialOptions.getSelected().getKey());
		tuple.put("tutorialOptions", tutorialOptions);

		tuple.put("sessionType", natures.getSelected().getKey());

		super.getResponse().setData(tuple);
	}
}
