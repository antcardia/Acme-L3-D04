/*
 * AuthenticatedUserAccountUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.helpers.UserIdentityHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerUserAccountUpdateService extends AbstractService<Lecturer, UserAccount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerUserAccountRepository repository;

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
		UserAccount userAccount;
		int principalId;

		principalId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findOneUserAccountById(principalId);
		// HINT: the following instruction forces the roles to be loaded.
		userAccount.getUserRoles().size(); // NOSONAR

		super.getBuffer().setData(userAccount);
	}

	@Override
	public void bind(final UserAccount object) {
		assert object != null;

		String password;
		String[] properties;

		properties = UserIdentityHelper.computeProperties();
		super.bind(object, properties);
		password = this.getRequest().getData("password", String.class);
		if (!password.equals("[MASKED-PASWORD]"))
			object.setPassword(password);
	}

	@Override
	public void validate(final UserAccount object) {
		assert object != null;

		int passwordLength;
		String password, confirmation;
		boolean isMatching;

		passwordLength = super.getRequest().getData("password", String.class).length();
		super.state(passwordLength >= 5 && passwordLength <= 60, "password", "acme.validation.length", 6, 60);

		password = super.getRequest().getData("password", String.class);
		confirmation = super.getRequest().getData("confirmation", String.class);
		isMatching = password.equals(confirmation);
		super.state(isMatching, "confirmation", "authenticated.user-account.form.error.confirmation-no-match");
	}

	@Override
	public void perform(final UserAccount object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserAccount object) {
		assert object != null;

		Tuple record;
		String[] properties;

		properties = UserIdentityHelper.computeProperties("username");
		record = super.unbind(object, properties);

		if (super.getRequest().getMethod().equals(HttpMethod.GET)) {
			record.put("password", "[MASKED-PASWORD]");
			record.put("confirmation", "[MASKED-PASWORD]");
		} else {
			record.put("password", super.getRequest().getData("password", String.class));
			record.put("confirmation", super.getRequest().getData("confirmation", String.class));
		}

		super.getResponse().setData(record);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
