
package acme.features.company.sessionpracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		final SessionPracticum ps = this.repository.findOnePracticumSessionById(super.getRequest().getData("id", int.class));
		status = super.getRequest().getPrincipal().hasRole(ps.getPracticum().getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;

		object = this.repository.findOnePracticumSessionById(super.getRequest().getData("id", int.class));
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation");

	}
	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("finishDate"))
			if (!MomentHelper.isBefore(object.getStartTime(), object.getFinishTime()))
				super.state(false, "startDate", "company.SessionPracticum.error.date.startAfterFinish");

	}
	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		final Tuple tuple;
		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional");
		tuple.put("draftMode", object.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
