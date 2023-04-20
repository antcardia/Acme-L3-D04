
package acme.features.auditor.audit_record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final Audit audit = this.repository.findAuditById(super.getRequest().getData("auditId", int.class));
		status = super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		List<AuditRecord> objects;
		int auditId;

		auditId = super.getRequest().getData("auditId", int.class);
		objects = this.repository.findAllAuditRecordsByAuditId(auditId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Tuple tuple;
		int auditId;
		Audit audit;
		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findAuditById(auditId);

		tuple = super.unbind(object, "subject");
		tuple.put("mark", object.getMark().toString());
		tuple.put("draft", object.getDraftMode());
		tuple.put("hours", object.getHoursFromPeriod());
		tuple.put("correction", object.getCorrection());

		super.getResponse().setData(tuple);
	}

}
