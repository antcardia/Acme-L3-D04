
package acme.features.lecturer.lecturerDashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Statistic;
import acme.datatypes.Nature;
import acme.forms.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final LecturerDashboard dashboard = new LecturerDashboard();
		int userAccountId;
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		final Lecturer lecturer = this.repository.findOneLecturerByUserAccountId(userAccountId);
		final double avgLearningTimeLectures = this.repository.averageLearningTimeOfLectures(lecturer).orElse(0.0);
		final double devLearningTimeLectures = this.repository.deviationLearningTimeOfLectures(lecturer).orElse(0.0);
		final double minLearningTimeLectures = this.repository.minimumLearningTimeOfLectures(lecturer).orElse(0.0);
		final double maxLearningTimeLectures = this.repository.maximumLearningTimeOfLectures(lecturer).orElse(0.0);

		dashboard.setAverageLearningTimeOfLectures(avgLearningTimeLectures);
		dashboard.setDeviationLearningTimeOfLectures(devLearningTimeLectures);
		dashboard.setMinimumLearningTimeOfLectures(minLearningTimeLectures);
		dashboard.setMaximumLearningTimeOfLectures(maxLearningTimeLectures);

		final Collection<Double> courseEstimatedLearningTime = this.repository.findManyEstimatedLearningTimeByCourse(lecturer);
		final Double average = Statistic.averageCalc(courseEstimatedLearningTime);
		final Double deviation = Statistic.deviationCalc(courseEstimatedLearningTime, average);
		final Double minimum = Statistic.minimumCalc(courseEstimatedLearningTime);
		final Double maximum = Statistic.maximumCalc(courseEstimatedLearningTime);
		dashboard.setAverageLearningTimeOfCourses(average);
		dashboard.setDeviationLearningTimeOfCourses(deviation);
		dashboard.setMinimumLearningTimeOfCourses(minimum);
		dashboard.setMaximumLearningTimeOfCourses(maximum);

		final Map<String, Integer> lecturesByType = new HashMap<String, Integer>();
		final Integer handsOnLectures = this.repository.totalLecturesByType(lecturer, Nature.HANDS_ON).orElse(0);
		final Integer theoreticalLectures = this.repository.totalLecturesByType(lecturer, Nature.THEORETICAL).orElse(0);
		lecturesByType.put("HANDS_ON", handsOnLectures);
		lecturesByType.put("THEORETICAL", theoreticalLectures);
		dashboard.setTotalLecturesByType(lecturesByType);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"totalLecturesByType", "averageLearningTimeOfLectures", // 
			"deviationLearningTimeOfLectures", "minimumLearningTimeOfLectures", //
			"maximumLearningTimeOfLectures", "averageLearningTimeOfCourses", // 
			"deviationLearningTimeOfCourses", "minimumLearningTimeOfCourses", // 
			"maximumLearningTimeOfCourses");

		super.getResponse().setData(tuple);
	}

}
