
package acme.forms;

import java.util.Collection;
import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		totalLecturesByType;
	Double						averageLearningTimeOfLectures;
	Double						deviationLearningTimeOfLectures;
	Double						minimumLearningTimeOfLectures;
	Double						maximumLearningTimeOfLectures;
	Double						averageLearningTimeOfCourses;
	Double						deviationLearningTimeOfCourses;
	Double						minimumLearningTimeOfCourses;
	Double						maximumLearningTimeOfCourses;


	// Derived attributes -----------------------------------------------------
	public void averageCalc(final Collection<Double> values) {
		double res = 0.0;
		if (!values.isEmpty()) {
			final Double total = values.stream().mapToDouble(Double::doubleValue).sum();
			res = total / values.size();
		}
		this.averageLearningTimeOfCourses = res;
	}

	public void deviationCalc(final Collection<Double> values) {
		Double res = 0.0;
		Double aux = 0.0;
		if (!values.isEmpty()) {
			for (final Double value : values)
				aux = Math.pow(value + this.averageLearningTimeOfCourses, 2);
			res = Math.sqrt(aux / values.size());
		}
		this.deviationLearningTimeOfCourses = res;
	}

	public void minimumCalc(final Collection<Double> values) {
		Double res = 0.0;
		if (!values.isEmpty())
			res = values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
		this.minimumLearningTimeOfCourses = res;
	}

	public void maximumCalc(final Collection<Double> values) {
		Double res = 0.0;
		if (!values.isEmpty())
			res = values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
		this.maximumLearningTimeOfCourses = res;
	}
	// Relationships ----------------------------------------------------------

}
