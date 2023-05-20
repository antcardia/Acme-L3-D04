
package acme.features.company.sessionPracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumDeleteService extends AbstractService<Company, SessionPracticum> {

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
		int practicumSessionId;
		Practicum practicum;
		SessionPracticum sessionPracticum;
		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumBySessionPracticumId(practicumSessionId);
		sessionPracticum = this.repository.findOneSessionPracticumById(practicumSessionId);
		status = practicum != null && sessionPracticum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
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
		Tuple tuple;
		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("confirmation", "false");

		super.getResponse().setData(tuple);
	}
}
