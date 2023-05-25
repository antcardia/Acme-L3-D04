
package acme.features.assistant.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionDeleteService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

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
		int masterId;
		Session session;
		Tutorial tutorial;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		masterId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(masterId);
		tutorial = session == null ? null : session.getTutorial();
		assistant = tutorial == null ? null : session.getTutorial().getAssistant();
		status = session != null && super.getRequest().getPrincipal().hasRole(assistant) && assistant == this.repository.findOneAssistantById(assistantId);

		super.getResponse().setAuthorised(status);
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

		super.bind(object, "title", "summary", "sessionType", "start", "end", "furtherInformation");
	}

	@Override
	public void validate(final Session object) {
		assert object != null;
	}

	@Override
	public void perform(final Session object) {
		assert object != null;
		final Tutorial tutorial = object.getTutorial();
		final int tutorialId = tutorial.getId();
		this.repository.delete(object);
		final Collection<Session> sessions = this.repository.findManySessionsByTutorialId(tutorialId);
		if (sessions.isEmpty()) {
			tutorial.setDraftMode(true);
			this.repository.save(tutorial);
		}
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
