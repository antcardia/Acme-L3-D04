
package acme.features.company.sessionpracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumShowService extends AbstractService<Company, SessionPracticum> {

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
		int practicumId;
		SessionPracticum sessionPracticum;
		final Practicum practicum;
		Company company;

		practicumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOnePracticumSessionById(practicumId);
		practicum = sessionPracticum == null ? null : sessionPracticum.getPracticum();
		company = practicum == null ? null : practicum.getCompany();
		status = sessionPracticum != null && //
			practicum != null && //
			super.getRequest().getPrincipal().hasRole(company) && //
			practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

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
	public void unbind(final SessionPracticum object) {
		assert object != null;

		Tuple tuple;
		Practicum practicum;
		boolean draftMode;

		practicum = object.getPracticum();
		draftMode = practicum.isDraftMode();

		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional");
		tuple.put("practicumId", practicum.getId());
		tuple.put("draftMode", draftMode);
		tuple.put("practicum", practicum);

		super.getResponse().setData(tuple);
	}
}
