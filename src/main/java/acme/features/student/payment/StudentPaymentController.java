
package acme.features.student.payment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.Payment;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentPaymentController extends AbstractController<Student, Payment> {

	@Autowired
	protected StudentPaymentCreateService createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
	}
}
