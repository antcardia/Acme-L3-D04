
package acme.forms;

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

	// Relationships ----------------------------------------------------------

}
