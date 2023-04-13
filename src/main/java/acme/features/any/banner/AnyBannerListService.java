
package acme.features.any.banner;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyBannerListService extends AbstractService<Any, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyBannerRepository repository;

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
		Banner banner;
		Date now;

		now = new Date();
		masterId = super.getRequest().getData("masterId", int.class);
		banner = this.repository.findOneBannerById(masterId);
		status = banner != null && banner.getStartPeriod().compareTo(now) >= 0 && banner.getStartPeriod().compareTo(now) >= 1;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Banner> objects;

		objects = this.repository.findAllBanners();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "startPeriod", "endPeriod", "linkPicture", "slogan", "linkWebDocument");

		super.getResponse().setData(tuple);
	}

}
