
package acme.features.authenticated.practica;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticaListService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticaRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Practicum> objects;
		objects = this.repository.findAllPracticas();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		final String courseTitle = object.getCourse().getTitle();

		tuple = super.unbind(object, "code", "title");
		tuple.put("courseTitle", courseTitle);

		super.getResponse().setData(tuple);
	}
}
