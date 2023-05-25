
package acme.features.authenticated.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.system.SystemConfiguration;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AuthenticatedAssistantCreateService extends AbstractService<Authenticated, Assistant> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAssistantRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Assistant object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object = new Assistant();
		object.setUserAccount(userAccount);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Assistant object) {
		assert object != null;

		super.bind(object, "supervisor", "expertiseFields", "resume", "furtherInformation");
	}

	@Override
	public void validate(final Assistant object) {
		assert object != null;

		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("supervisor")) {
			final String supervisor = object.getSupervisor();
			super.state(!antiSpam.isSpam(supervisor), "supervisor", "authenticated.assistant.form.error.spamSupervisor");
		}

		if (!super.getBuffer().getErrors().hasErrors("expertiseFields")) {
			final String expertiseFields = object.getExpertiseFields();
			super.state(!antiSpam.isSpam(expertiseFields), "expertiseFields", "authenticated.assistant.form.error.spamExpertiseFields");
		}

		if (!super.getBuffer().getErrors().hasErrors("resume")) {
			final String resume = object.getResume();
			super.state(!antiSpam.isSpam(resume), "resume", "authenticated.assistant.form.error.spamResume");
		}

		if (!super.getBuffer().getErrors().hasErrors("furtherInformation")) {
			final String furtherInformation = object.getFurtherInformation();
			super.state(!antiSpam.isSpam(furtherInformation), "furtherInformation", "authenticated.assistant.form.error.spamFurtherInformation");
		}
	}

	@Override
	public void perform(final Assistant object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Assistant object) {
		Tuple tuple;

		tuple = super.unbind(object, "supervisor", "expertiseFields", "resume", "furtherInformation");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
