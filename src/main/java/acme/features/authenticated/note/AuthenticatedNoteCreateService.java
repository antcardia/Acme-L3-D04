
package acme.features.authenticated.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notes.Note;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteRepository repository;

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
		Note object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object = new Note();
		object.setMoment(MomentHelper.getCurrentMoment());
		object.setAuthor(userAccount.getUsername() + " - " + userAccount.getIdentity().getSurname() + ", " + userAccount.getIdentity().getName());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "moment", "title", "author", "message", "email", "furtherInformation");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			final String title = object.getTitle();
			super.state(!antiSpam.isSpam(title), "title", "authenticated.note.form.error.spamTitle");
		}

		if (!super.getBuffer().getErrors().hasErrors("message")) {
			final String message = object.getMessage();
			super.state(!antiSpam.isSpam(message), "message", "authenticated.note.form.error.spamMessage");
		}

		if (!super.getBuffer().getErrors().hasErrors("email"))
			super.state(object.getEmail().length() < 255, "email", "authenticated.note.form.error.outOfRangeEmail");

		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(object.getFurtherInformation().length() < 255, "furtherInformation", "authenticated.note.form.error.outOfRangeLink");

		super.state(super.getRequest().getData("confirmation", boolean.class), "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		object.setMoment(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "author", "message", "email", "furtherInformation");
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}
}
