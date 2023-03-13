
package acme.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalAudits;
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
