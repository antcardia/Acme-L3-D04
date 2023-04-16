
package acme.features.authenticated.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedOfferController extends AbstractController<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferListService		service;

	@Autowired
	protected AuthenticatedOfferShowService		showService;

	@Autowired
	protected AuthenticatedOfferCreateService	createService;

	@Autowired
	protected AuthenticatedOfferDeleteService	deleteService;

	@Autowired
	protected AuthenticatedOfferUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.service);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
