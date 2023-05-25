
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Banner object;

		object = new Banner();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "moment", "startPeriod", "endPeriod", "linkPicture", "slogan", "linkWebDocument");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			final Date startPeriod = object.getStartPeriod();
			super.state(startPeriod.compareTo(object.getMoment()) > 0, "startPeriod", "administrator.banner.form.error.badDate1");
		}
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			final Date endPeriod = object.getEndPeriod();
			super.state(endPeriod.compareTo(object.getStartPeriod()) > 0, "startPeriod", "administrator.banner.form.error.badDate2");
			super.state(MomentHelper.isLongEnough(endPeriod, object.getStartPeriod(), 7, ChronoUnit.DAYS), "endPeriod", "administrator.banner.form.error.badDate3");
		}

		if (!super.getBuffer().getErrors().hasErrors("linkPicture")) {
			final String linkPicture = object.getLinkPicture();
			super.state(!antiSpam.isSpam(linkPicture), "linkPicture", "administrator.banner.form.error.spamLink");
		}

		if (!super.getBuffer().getErrors().hasErrors("slogan")) {
			final String slogan = object.getLinkPicture();
			super.state(!antiSpam.isSpam(slogan), "slogan", "administrator.banner.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("linkWebDocument")) {
			final String linkWebDocument = object.getLinkPicture();
			super.state(!antiSpam.isSpam(linkWebDocument), "linkWebDocument", "administrator.banner.form.error.spamLink");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "startPeriod", "endPeriod", "linkPicture", "slogan", "linkWebDocument");

		super.getResponse().setData(tuple);
	}

}
