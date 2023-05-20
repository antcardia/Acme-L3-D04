
package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peeps.Peep;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepRepository repository;

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
		Peep object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();

		object = new Peep();
		object.setInstantiation(MomentHelper.getCurrentMoment());
		if (!principal.isAnonymous()) {
			userAccountId = principal.getAccountId();
			userAccount = this.repository.findUserAccountById(userAccountId);
			object.setNick(userAccount.getIdentity().getSurname() + ", " + userAccount.getIdentity().getName());
		}
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "insatiation", "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "any.peep.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("nick")) {
			final String nick = object.getNick();
			super.state(!antiSpam.isSpam(nick), "nick", "any.peep.form.error.spamNick");
		}

		if (!super.getBuffer().getErrors().hasErrors("message")) {
			final String message = object.getMessage();
			super.state(!antiSpam.isSpam(message), "message", "any.peep.form.error.spamMessage");
		}

		if (!super.getBuffer().getErrors().hasErrors("email")) {
			final String email = object.getEmail();
			super.state(!antiSpam.isSpam(email), "email", "any.peep.form.error.spamEmail");
		}

		if (!super.getBuffer().getErrors().hasErrors("link")) {
			final String link = object.getLink();
			super.state(!antiSpam.isSpam(link), "link", "any.peep.form.error.spamLink");
		}
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		object.setInstantiation(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "title", "nick", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}
