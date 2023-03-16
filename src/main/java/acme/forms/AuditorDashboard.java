
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalTheoreticalAudits;
	Integer						totalHandsOnAudits;
	Double						averageNumAuditRecordsInAudit;
	Double						deviationNumAuditRecordsInAudit;
	Double						maxNumAuditRecordsInAudit;
	Double						minNumAuditRecordsInAudit;
	Double						averagePeriodLength;
	Double						deviationPeriodLength;
	Double						minumumPeriodLength;
	Double						maximumPeriodLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
