/*
 * AuthenticatedUserAccountController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.AbstractController;

import acme.roles.Company;

@Controller
public class CompanyUserAccountController extends AbstractController<Company, Company> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyUserAccountController updateService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("update", this.updateService);
	}
}
