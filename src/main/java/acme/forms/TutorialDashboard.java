
package acme.forms;

import java.util.Map;

import acme.datatypes.Nature;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorialDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Nature, Integer>		totalTutorialsByType;

	protected Double			averageSessionTime;

	protected Double			deviationSessionTime;

	protected Double			minimumSessionTime;

	protected Double			maximumSessionTime;

	protected Double			averageTutorialActivities;

	protected Double			deviationTutorialActivities;

	protected Double			minimumTutorialActivities;

	protected Double			maximumTutorialActivities;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
