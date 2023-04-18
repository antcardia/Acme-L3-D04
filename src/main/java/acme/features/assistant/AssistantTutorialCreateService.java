/*
 * AuthenticatedProviderCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant;

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
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


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
		Tutorial object;
		Assistant assistant;
		final Course course;

		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		//course = new Course();
		object = new Tutorial();
		object.setDraftMode(true);
		object.setAssistant(assistant);
		//object.setCourse(course);

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
			super.state(!antiSpam.isSpam(title), "title", "lecturer.course.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("abstract$")) {
			final String summary = object.getSummary();
			super.state(!antiSpam.isSpam(summary), "summary", "assistant.tutorial.form.error.spamAbstract");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;

			existing = this.repository.findOneTutorialByCode(object.getCode());
			super.state(existing == null, "code", "assistant.tutorial.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("estimatedTime"))
			super.state(object.getEstimatedTime() > 0 && object.getEstimatedTime() < 1000000, "estimatedTime", "assistant.tutorial.form.error.outOfRangeRetailPrice");
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

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

		System.out.println(choices.getSelected());
		System.out.println(choices.getSelected().getKey());
		System.out.println(choices.getSelected().getLabel());
		System.out.println(tuple);

		super.getResponse().setData(tuple);
	}

}
