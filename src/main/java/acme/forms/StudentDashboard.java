
package acme.forms;

import java.util.Map;

import acme.datatypes.Nature;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	protected Map<Nature, Integer>	numberOfActivity;

	protected Double				averageLearningTime;

	protected Double				deviationLearningTime;

	protected Double				minimumLearningTime;

	protected Double				maximumLearningTime;

	protected Double				averagePeriodActivities;

	protected Double				deviationPeriodActivities;

	protected Double				minimumPeriodActivities;

	protected Double				maximumPeriodActivities;

}
