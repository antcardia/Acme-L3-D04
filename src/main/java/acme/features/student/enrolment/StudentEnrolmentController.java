
package acme.features.student.enrolment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolment.Enrolment;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentEnrolmentController extends AbstractController<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentListService	service;

	@Autowired
	protected StudentEnrolmentShowService	showService;

	@Autowired
	protected StudentEnrolmentCreateService	createService;

	@Autowired
	protected StudentEnrolmentDeleteService	deleteService;

	@Autowired
	protected StudentEnrolmentUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.service);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

	}
}
