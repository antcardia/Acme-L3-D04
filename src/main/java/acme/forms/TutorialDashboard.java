
package acme.forms;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class TutorialDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Nature, Integer>		totalTutorialsByType;
	Statistic					calculationsOfTutorials;
	Statistic					calculationOfSessions;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
