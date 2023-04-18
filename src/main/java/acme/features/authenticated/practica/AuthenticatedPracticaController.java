
package acme.features.authenticated.practica;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedPracticaController extends AbstractController<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticaListService	service;

	@Autowired
	protected AuthenticatedPracticaShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.service);
		super.addBasicCommand("show", this.showService);
	}
}
