
package acme.features.authenticated.sessionpracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumShowService extends AbstractService<Company, SessionPracticum> {

	// Constants --------------------------------------------------------------
	protected static final String[]				PROPERTIES	= {
		"title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional", "draftMode"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanySessionPracticumRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionPracticumId;
		SessionPracticum sessionPracticum;
		Practicum practicum;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);

		status = practicum != null && (!practicum.isDraftMode() && sessionPracticum.isDraftMode() || principal.hasRole(practicum.getCompany()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum sessionPracticum;
		int sessionPracticumId;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);

		super.getBuffer().setData(sessionPracticum);
	}

	@Override
	public void unbind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		Practicum practicum;
		Tuple tuple;

		practicum = sessionPracticum.getPracticum();
		tuple = super.unbind(sessionPracticum, CompanySessionPracticumUpdateService.PROPERTIES_UNBIND);
		tuple.put("masterId", practicum.getId());
		tuple.put("draftMode", practicum.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
