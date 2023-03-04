
package acme.dashboards;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class studentDashboard extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	protected Integer			numberOfActivity;

	protected Double			averagePeriodActivities;

	protected Double			minimunPeriodActivities;

	protected Double			maximunPeriodActivities;

	protected Double			deviationPeriodActivities;

	protected Double			averageLearningTime;

	protected Double			minimunLearningTime;

	protected Double			maximunLearningTime;

	protected Double			deviationLearningTime;

}
