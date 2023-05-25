
package acme.features.student.activity;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import antiSpamFilter.AntiSpamFilter;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		final Collection<Enrolment> list = this.repository.findAllEnrolmentByStudentId(super.getRequest().getPrincipal().getActiveRoleId());
		final Integer enrolmentId = super.getRequest().getData("masterId", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(enrolmentId);

		status = list.contains(enrolment);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;

		object = new Activity();

		final Integer enrolmentId = super.getRequest().getData("masterId", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(enrolmentId);
		object.setEnrolment(enrolment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "tittle", "abstract$", "workbookName", "startTime", "finishTime", "link");
		Nature atype;
		atype = super.getRequest().getData("atype", Nature.class);
		object.setAtype(atype);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());
		if (!super.getBuffer().getErrors().hasErrors("tittle")) {
			final String motivation = object.getTittle();
			super.state(!antiSpam.isSpam(motivation), "tittle", "student.activity.form.error.spamTitle");
		}
		if (!super.getBuffer().getErrors().hasErrors("workbookName")) {
			final String goals = object.getWorkbookName();
			super.state(!antiSpam.isSpam(goals), "workbookName", "student.activity.form.error.spamTitle2");
		}
		if (!super.getBuffer().getErrors().hasErrors("abstract$")) {
			final String goals = object.getAbstract$();
			super.state(!antiSpam.isSpam(goals), "abstract$", "student.activity.form.error.spamTitle3");
		}
		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("finishTime")) {
			final Date startTime = object.getStartTime();
			final Date finishTime = object.getFinishTime();
			super.state(startTime != MomentHelper.getCurrentMoment() && MomentHelper.isBefore(startTime, finishTime), "startTime", "student.activity.form.error.startTime");
			super.state(finishTime != MomentHelper.getCurrentMoment() && MomentHelper.isBefore(startTime, finishTime), "finishTime", "student.activity.form.error.finishTime");
		}
		if (object.getAtype() != null)
			if (!super.getBuffer().getErrors().hasErrors("lectureType"))
				super.state(!object.getAtype().equals(Nature.BALANCED), "atype", "student.activity.form.error.atype");
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(object.getLink().length() < 255, "link", "student.activity.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "tittle", "abstract$", "workbookName", "startTime", "finishTime", "link");

		final SelectChoices choices = SelectChoices.from(Nature.class, object.getAtype());
		tuple.put("atype", choices.getSelected().getKey());
		tuple.put("activityType", choices);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().setData(tuple);

	}

}
