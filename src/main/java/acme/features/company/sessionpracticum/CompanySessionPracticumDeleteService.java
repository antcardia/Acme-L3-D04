
package acme.features.company.sessionpracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumDeleteService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		SessionPracticum ps;

		ps = this.repository.findOnePracticumSessionById(super.getRequest().getData("id", int.class));

		status = ps != null && super.getRequest().getPrincipal().hasRole(ps.getPracticum().getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSessionById(id);

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

	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional");
		super.getResponse().setData(tuple);
	}
}
