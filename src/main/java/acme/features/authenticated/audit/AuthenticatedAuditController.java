
package acme.features.authenticated.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audits.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedAuditController extends AbstractController<Authenticated, Audit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditListService	listService;

	@Autowired
	protected AuthenticatedAuditShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
