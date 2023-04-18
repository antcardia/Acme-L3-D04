
package acme.features.authenticated.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticaShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticaRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticaById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		final String courseTitle = object.getCourse().getTitle();
		final String companyName = object.getCompany().getUserAccount().getUsername();

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime");
		tuple.put("courseTitle", courseTitle);
		tuple.put("companyName", companyName);

		super.getResponse().setData(tuple);
	}
}
