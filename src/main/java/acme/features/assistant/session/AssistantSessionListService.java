
package acme.features.assistant.session;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Session;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionListService extends AbstractService<Assistant, Session> {

	@Autowired
	protected AssistantSessionRepository repository;


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
		Collection<Session> objects;
		Assistant principal;

		principal = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());

		objects = this.repository.findAllSession().stream().filter(x -> x.getTutorial().getAssistant() == principal).collect(Collectors.toList());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "sessionType");

		super.getResponse().setData(tuple);
	}
}
