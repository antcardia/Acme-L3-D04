
package acme.features.company.sessionPracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumShowService extends AbstractService<Company, SessionPracticum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanySessionPracticumRepository repository;


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
		final int practicumSessionId;
		Practicum practicum;
		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumBySessionPracticumId(practicumSessionId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		SessionPracticum object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionPracticumById(id);
		super.getBuffer().setData(object);
	}
	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("confirmation", "false");

		super.getResponse().setData(tuple);
	}
}
