
package acme.forms;

import java.util.List;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	List<Integer>				totalPracticasWithTheoryOrHandsOnByMonth;

	protected Double			averageSessionPracticumTime;

	protected Double			deviationSessionPracticumTime;

	protected Double			minimumSessionPracticumTime;

	protected Double			maximumSessionPracticumTime;

	protected Double			averagePracticaLength;

	protected Double			deviationPracticaLength;

	protected Double			minimumPracticaLength;

	protected Double			maximumPracticaLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
