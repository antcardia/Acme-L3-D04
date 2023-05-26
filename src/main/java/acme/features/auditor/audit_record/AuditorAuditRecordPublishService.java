
package acme.features.auditor.audit_record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.audits.AuditRecord;
import acme.entities.system.SystemConfiguration;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import antiSpamFilter.AntiSpamFilter;

@Service
public class AuditorAuditRecordPublishService extends AbstractService<Auditor, AuditRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		final AuditRecord ar = this.repository.findAuditRecordById(super.getRequest().getData("id", int.class));
		status = super.getRequest().getPrincipal().hasRole(ar.getAudit().getAuditor()) && ar.getDraftMode();

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
	public void bind(final AuditRecord object) {
		assert object != null;

		final String mark = super.getRequest().getData("mark", String.class);
		super.bind(object, "subject", "assessment", "link", "periodStart", "periodEnd", "draftMode");
		object.setMark(Mark.parse(mark));
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		final SystemConfiguration config = this.repository.findSystemConfiguration();
		final AntiSpamFilter antiSpam = new AntiSpamFilter(config.getThreshold(), config.getSpamWords());

		if (!super.getBuffer().getErrors().hasErrors("periodStart") && !super.getBuffer().getErrors().hasErrors("startDate"))
			if (!MomentHelper.isBefore(object.getPeriodStart(), object.getPeriodEnd()))
				super.state(false, "periodStart", "auditor.auditrecord.error.date.startAfterFinish");
			else
				super.state(object.getHoursFromPeriod() > 1.0, "startDate", "auditor.auditrecord.error.date.shortPeriod");
		if (!super.getBuffer().getErrors().hasErrors("subject")) {
			final String conclusion = object.getSubject();
			super.state(!antiSpam.isSpam(conclusion), "subject", "auditor.auditrecord.form.error.spamsubject");
		}
		if (!super.getBuffer().getErrors().hasErrors("assessment")) {
			final String conclusion = object.getAssessment();
			super.state(!antiSpam.isSpam(conclusion), "assessment", "auditor.auditrecord.form.error.spamassessment");
		}
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(object.getLink().length() < 255, "link", "auditor.auditrecord.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		final Tuple tuple;
		final SelectChoices marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "link", "mark", "periodStart", "periodEnd");
		tuple.put("marks", marks);

		super.getResponse().setData(tuple);
	}

}
