
package acme.features.student.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.entities.system.SystemConfiguration;
import acme.forms.Payment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import antiSpamFilter.AntiSpamFilter;

@Service
public class StudentPaymentCreateService extends AbstractService<Student, Payment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentPaymentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Enrolment enrolment;
		int id;
		id = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		final Principal usuario = super.getRequest().getPrincipal();
		final int userId = usuario.getAccountId();
		super.getResponse().setAuthorised(enrolment.getStudent().getUserAccount().getId() == userId && enrolment.isDraftMode());
	}

	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Payment payment;
		payment = new Payment();

		super.getBuffer().setData(payment);
	}

	@Override
	public void bind(final Payment object) {
		assert object != null;

		super.bind(object, "holderName", "creditCard", "expirationDate", "securityCode");

	}

	@Override
	public void validate(final Payment object) {
		assert object != null;
		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());
		if (!super.getBuffer().getErrors().hasErrors("holderName")) {
			final String holderName = object.getHolderName();
			super.state(!antiSpam.isSpam(holderName), "holderName", "student.payment.form.error.spamTitle");
		}
	}

	@Override
	public void perform(final Payment object) {
		assert object != null;
		int enrolmentId;
		final Enrolment enrolment;
		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		enrolment.setHolderName(object.getHolderName());
		final String creditCardNumber = object.getCreditCard();
		final String nibble = creditCardNumber.substring(creditCardNumber.length() - 4);
		enrolment.setCreditCardFourLowNibble(nibble);
		enrolment.setDraftMode(false);
		this.repository.save(enrolment);
	}

	@Override
	public void unbind(final Payment object) {
		assert object != null;
		Tuple tuple;
		int enrolmentId;
		enrolmentId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "holderName", "creditCard", "expirationDate", "securityCode");
		tuple.put("masterId", enrolmentId);

		super.getResponse().setData(tuple);

	}
}
