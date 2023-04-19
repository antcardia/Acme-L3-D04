/*
 * EmployerApplicationShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.session;

import java.util.Collection;
import java.util.stream.Collectors;

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
public class AssistantSessionShowService extends AbstractService<Assistant, Session> {

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

		masterId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(masterId);
		status = session != null;

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
	public void unbind(final Session object) {
		assert object != null;

		Tuple tuple;
		SelectChoices natures;
		SelectChoices tutorialOptions;
		Collection<Tutorial> tutorials;
		Boolean status;
		final Assistant assistant;
		Tutorial objectTutorial;

		objectTutorial = object.getTutorial();
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		status = object.getTutorial().getAssistant().getId() == assistant.getId();

		tutorials = this.repository.findAllTutorial().stream().filter(x -> x.getAssistant() == assistant).collect(Collectors.toList());

		natures = SelectChoices.from(Nature.class, object.getSessionType());

		tuple = super.unbind(object, "title", "summary", "sessionType", "start", "end", "furtherInformation");
		tuple.put("sessionTypes", natures);
		if (status) {
			tutorialOptions = SelectChoices.from(tutorials, "code", object.getTutorial());
			tuple.put("tutorial", tutorialOptions.getSelected().getKey());
			tuple.put("tutorialOptions", tutorialOptions);
		} else
			tuple.put("tutorial", objectTutorial.getCode());

		tuple.put("sessionType", natures.getSelected().getKey());
		tuple.put("status", status);

		super.getResponse().setData(tuple);
	}

}
