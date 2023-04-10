
package acme.features.administrator.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.AbstractController;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;

@Controller
public class AdministratorBulletinController extends AbstractController<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinCreateService createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
	}
}
