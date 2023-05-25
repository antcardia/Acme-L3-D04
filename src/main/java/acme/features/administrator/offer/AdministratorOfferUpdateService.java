
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface -------------------------------------


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
			super.state(!antiSpam.isSpam(title), "heading", "administrator.offer.form.error.spam1");
		}

		if (!super.getBuffer().getErrors().hasErrors("abstract$")) {
			final String message = object.getAbstract$();
			super.state(!antiSpam.isSpam(message), "abstract$", "administrator.offer.form.error.spam2");
		}
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(object.getLink().length() < 255, "link", "administrator.offer.form.error.outOfRangeLink");

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.price");

		if (!super.getBuffer().getErrors().hasErrors("startDay") && !super.getBuffer().getErrors().hasErrors("lastDay")) {
			final Date startDay = object.getStartDay();
			final Date lastDay = object.getLastDay();
			final Date instantiationMoment = object.getInstantiationMoment();

			super.state(!startDay.equals(instantiationMoment) && MomentHelper.isBefore(instantiationMoment, startDay) && MomentHelper.isLongEnough(startDay, instantiationMoment, 1, ChronoUnit.DAYS), "startDay", "administrator.offer.form.error.startDay");
			super.state(MomentHelper.isLongEnough(lastDay, startDay, 7, ChronoUnit.DAYS), "lastDay", "administrator.offer.form.error.lastDay");

		}
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
