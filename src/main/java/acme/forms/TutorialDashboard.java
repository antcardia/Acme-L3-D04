
package acme.forms;

import java.util.Map;

import acme.datatypes.SessionType;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class TutorialDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<SessionType, Integer>	totalTutorialsByType;
	Statistic					calculationsOfTutorials;
	Statistic					calculationOfSessions;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
