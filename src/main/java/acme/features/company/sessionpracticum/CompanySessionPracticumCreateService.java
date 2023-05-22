
package acme.features.company.sessionpracticum;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumCreateService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("practicumId", int.class);
		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {

		boolean status;
		final Practicum practicum = this.repository.findOnePracticumById(super.getRequest().getData("practicumId", int.class));
		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && practicum.isDraftMode();

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		SessionPracticum object;

		object = new SessionPracticum();
		object.setIsAddendum(false);
		super.getBuffer().setData(object);

	}
	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		final int practicumId = super.getRequest().getData("practicumId", int.class);
		final Practicum practicum = this.repository.findOnePracticumById(practicumId);
		super.bind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "addendum");
		object.setPracticum(practicum);
	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;

		if (!(super.getBuffer().getErrors().hasErrors("startTime") || super.getBuffer().getErrors().hasErrors("finishTime"))) {
			final boolean finishTimeIsAfter = object.getFinishTime().after(object.getStartTime());
			final Date currentDate = MomentHelper.getCurrentMoment();
			final Long durationSinceCurrentDate = MomentHelper.computeDuration(currentDate, object.getStartTime()).toDays();
			final boolean startTimeIsOneDayAhead = durationSinceCurrentDate.doubleValue() >= 1.;
			super.state(finishTimeIsAfter, "finishTime", "company.session.form.error.finishTime");

		}

	}
	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "addendum");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		super.getResponse().setData(tuple);
	}
	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
