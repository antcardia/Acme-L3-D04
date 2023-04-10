
package acme.features.authenticated.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.AbstractController;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;

@Controller
public class AuthenticatedBulletinController extends AbstractController<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinListService	service;

	@Autowired
	protected AuthenticatedBulletinShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.service);
		super.addBasicCommand("show", this.showService);
	}
}
