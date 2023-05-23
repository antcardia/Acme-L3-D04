
package acme.features.authenticated.lecturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AuthenticatedLecturerUpdateService extends AbstractService<Authenticated, Lecturer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedLecturerRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Lecturer object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneLecturerByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecturer object) {
		assert object != null;

		super.bind(object, "almaMater", "resume", "listOfQualifications", "furtherInformation");
	}

	@Override
	public void validate(final Lecturer object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("almaMater")) {
			final String almaMater = object.getAlmaMater();
			super.state(!antiSpam.isSpam(almaMater), "almaMater", "authenticated.lecturer.form.error.spamAlmaMater");
		}

		if (!super.getBuffer().getErrors().hasErrors("resume")) {
			final String resume = object.getResume();
			super.state(!antiSpam.isSpam(resume), "resume", "authenticated.lecturer.form.error.spamResume");
		}

		if (!super.getBuffer().getErrors().hasErrors("listOfQualifications")) {
			final String listOfQualifications = object.getListOfQualifications();
			super.state(!antiSpam.isSpam(listOfQualifications), "listOfQualifications", "authenticated.lecturer.form.error.spamListOfQualifications");
		}
		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(object.getFurtherInformation().length() < 255, "furtherInformation", "authenticated.lecturer.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final Lecturer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecturer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "almaMater", "resume", "listOfQualifications", "furtherInformation");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
