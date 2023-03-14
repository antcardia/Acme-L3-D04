
package acme.forms;

import java.util.Map;

import acme.datatypes.UserIdentity;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractForm;

public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<UserIdentity, Integer>		totalNumberOfPrincipalsByRole;
	Double					peepsWithEmailAddressAndLinkRatio;
	Double					criticalBulletinsRatio;
	Double					nonCriticalBulletinsRatio;
	Map<Money, Double>			averageBudgetByCurrency;
	Map<Money, Double>			minBudgetByCurrency;
	Map<Money, Double>			maxBudgetByCurrency;
	Map<Money, Double>			budgetDeviationByCurrency;
	Double					averageNotesPosted;
	Double					minNotesPosted;
	Double					maxNotesPosted;
	Double					notesPostedDeviation;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	{

	}
}
