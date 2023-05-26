
package acme.features.auditor.audit_record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.audits.AuditRecord;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordShowService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		AuditRecord ar;

		ar = this.repository.findAuditRecordById(super.getRequest().getData("id", int.class));

		status = ar != null && super.getRequest().getPrincipal().hasRole(ar.getAudit().getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);

		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		final String format = "yyyy/MM/dd hh:mm";

		Tuple tuple;

		final SelectChoices choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "link", "draftMode", "correction");
		tuple.put("periodStart", MomentHelper.format(format, object.getPeriodStart()));
		tuple.put("periodEnd", MomentHelper.format(format, object.getPeriodEnd()));
		tuple.put("hours", object.getHoursFromPeriod());
		tuple.put("marks", choices);

		super.getResponse().setData(tuple);
	}
}
