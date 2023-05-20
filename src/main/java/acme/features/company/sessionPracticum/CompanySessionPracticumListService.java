
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumListService extends AbstractService<Company, SessionPracticum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanySessionPracticumRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		final Practicum practicum;
		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		Collection<SessionPracticum> objects;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyPracticumSessionsByMasterId(masterId);
		super.getBuffer().setData(objects);
	}
	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		Tuple tuple;
		String addendumState;

		if (object.getIsAddendum())
			addendumState = "x";
		else
			addendumState = "";

		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation");
		tuple.put("addendumState", addendumState);

		super.getResponse().setData(tuple);
	}
	@Override
	public void unbind(final Collection<SessionPracticum> objects) {
		assert objects != null;

		int masterId;
		Practicum practicum;
		boolean existingAddendum;
		boolean showCreate;
		boolean showAddendumCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		showCreate = practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		existingAddendum = this.repository.findOneAddendumSessionByPracticumId(masterId) != null ? false : true;
		showAddendumCreate = !practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && existingAddendum;

		super.getResponse().setGlobal("masterId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("showAddendumCreate", showAddendumCreate);

	}

}
