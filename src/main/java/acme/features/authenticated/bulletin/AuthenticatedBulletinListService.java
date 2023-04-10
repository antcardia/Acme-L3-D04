
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.AbstractService;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


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
		Collection<Bulletin> objects;
		objects = this.repository.findAllBulletin();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "message");

		super.getResponse().setData(tuple);
	}
}
