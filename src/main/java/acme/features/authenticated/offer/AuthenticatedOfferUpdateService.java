
package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AuthenticatedOfferUpdateService extends AbstractService<Authenticated, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository repository;

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
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "heading", "instantiationMoment", "abstract$", "startDay", "lastDay", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("heading")) {
			final String title = object.getHeading();
			super.state(!antiSpam.isSpam(title), "heading", "authenticated.offer.form.error.spam1");
		}

		if (!super.getBuffer().getErrors().hasErrors("abstract$")) {
			final String message = object.getAbstract$();
			super.state(!antiSpam.isSpam(message), "abstract$", "authenticated.offer.form.error.spam2");
		}

		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(object.getLink().length() < 255, "link", "authenticated.offer.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "heading", "instantiationMoment", "abstract$", "startDay", "lastDay", "price", "link");

		super.getResponse().setData(tuple);
	}

}
