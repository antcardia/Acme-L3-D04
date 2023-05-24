
package acme.features.company.sessionpracticum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicum.SessionPracticum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanySessionPracticumController extends AbstractController<Company, SessionPracticum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanySessionPracticumShowService		showService;
	@Autowired
	private CompanySessionPracticumCreateService	createService;
	@Autowired
	private CompanySessionPracticumUpdateService	updateService;
	@Autowired
	private CompanySessionPracticumDeleteService	deleteService;
	@Autowired
	private CompanySessionPracticumListService		listService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
	}
}
