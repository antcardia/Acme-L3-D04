
package acme.features.company.sessionPracticum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicum.SessionPracticum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class SessionPracticumController extends AbstractController<Company, SessionPracticum> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionPracticumListService			listService;

	@Autowired
	protected CompanySessionPracticumShowService			showService;

	@Autowired
	protected CompanySessionPracticumCreateService			createService;

	@Autowired
	protected CompanySessionPracticumUpdateService			updateService;

	@Autowired
	protected CompanySessionPracticumDeleteService			deleteService;

	@Autowired
	protected CompanySessionPracticumCreateAddendumService	createAddencumService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("create-addendum", "create", this.createAddencumService);
	}

}
