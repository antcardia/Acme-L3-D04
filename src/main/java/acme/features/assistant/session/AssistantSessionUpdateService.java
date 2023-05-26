
package acme.features.assistant.session;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.system.SystemConfiguration;
import acme.entities.tutorial.Session;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AssistantSessionUpdateService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		final boolean status;
		final Assistant assistant;
		int assistantId;
		Session session;
		int id;

		id = super.getRequest().getData("id", int.class);

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		session = this.repository.findOneSessionById(id);
		assistant = session.getTutorial().getAssistant();

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
		Session object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		Nature sessionType;
		sessionType = super.getRequest().getData("sessionType", Nature.class);
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
		assert object != null;

		Tuple tuple;
		SelectChoices natures;
		Boolean status;
		final Assistant assistant;

		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		status = object.getTutorial().getAssistant().getId() == assistant.getId();
		natures = SelectChoices.from(Nature.class, object.getSessionType());

		tuple = super.unbind(object, "title", "summary", "sessionType", "start", "end", "furtherInformation");
		tuple.put("sessionTypes", natures);

		tuple.put("sessionType", natures.getSelected().getKey());
		tuple.put("status", status);

		super.getResponse().setData(tuple);
	}
}
