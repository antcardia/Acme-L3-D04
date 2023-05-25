
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {

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
		int masterId;
		Tutorial tutorial;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		masterId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(assistant) && assistant == this.repository.findOneAssistantById(assistantId);

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
		Course course;
		final int courseId = super.getRequest().getData("course", int.class);

		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "summary", "goals", "estimatedTime", "draftMode");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		final Collection<Session> sessions = this.repository.findManySessionsByTutorialId(object.getId());

		for (final Session s : sessions)
			this.repository.delete(s);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		Tuple tuple;
		final String assistantName = object.getAssistant().getUserAccount().getUsername();
		final String courseTitle = object.getCourse().getTitle();

		tuple = super.unbind(object, "code", "title", "summary", "goals", "estimatedTime", "draftMode");
		tuple.put("assistantName", assistantName);
		tuple.put("courseTitle", courseTitle);

		super.getResponse().setData(tuple);
	}

}
