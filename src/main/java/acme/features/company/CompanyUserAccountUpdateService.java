
package acme.features.company;

import org.hibernate.cfg.BinderHelper;

/*
 * AuthenticatedConsumerUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * @@ -10,110 +13,78 @@
 * they accept any liabilities with respect to them.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.AbstractService;

import acme.framework.helpers.PrincipalHelper;
import acme.roles.Company;

@Service
public class CompanyUserAccountUpdateService extends AbstractService<Company, Company> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyUserAccountRepository repository;

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
		Company object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneCompanyByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Company object) {
		assert object != null;

		super.bind(object, "name", "VATnumber", "summary", "link");
	}

	@Override
	public void validate(final Company object) {
		assert object != null;
	}

	@Override
	public void perform(final Company object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Company object) {
		assert object != null;

		Tuple tuple;

		tuple = BinderHelper.unbind(object, "name", "VATnumber", "summary", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
