
package acme.forms;

import java.util.Map;

import acme.datatypes.UserIdentity;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractForm;

public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<UserIdentity, Integer>	totalNumberOfPrincipalsByRole;
	Double						peepsWithEmailAddressAndLinkRatio;
	Double						criticalBulletinsRatio;
	Double						nonCriticalBulletinsRatio;
	Map<Money, Double>			averageBudgetDeviationByCurrency;
	Map<Money, Double>			minBudgetDeviationByCurrency;
	Map<Money, Double>			maxBudgetDeviationByCurrency;
	Map<Money, Double>			budgetDeviationByCurrency;
	Double						averageNotesPostedDeviation;
	Double						minNotesPostedDeviation;
	Double						maxNotesPostedDeviation;
	Double						notesPostedDeviation;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	{

	}
}
