
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		totalNumberOfPrincipalsByRole;
	Double						peepsWithEmailAddressAndLinkRatio;
	Double						criticalBulletinsRatio;
	Double						nonCriticalBulletinsRatio;
	Map<String, Double>			averageBudgetByCurrency;
	Map<String, Double>			minBudgetByCurrency;
	Map<String, Double>			maxBudgetByCurrency;
	Map<String, Double>			budgetDeviationByCurrency;
	Double						averageNotesPosted;
	Double						minNotesPosted;
	Double						maxNotesPosted;
	Double						notesPostedDeviation;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
