/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(l) from Lecture l where l.lectureType = acme.datatypes.Nature.THEORETICAL")
	Integer totalTheoreticalLectures();

	@Query("select count(l) from Lecture l where l.lectureType = acme.datatypes.Nature.HANDS_ON")
	Integer totalHandsOnLectures();

	@Query("select avg(l.estimatedLearningTime) from Lecture l")
	Double averageLearningTimeOfLectures();

	@Query("select stdev(l.estimatedLearningTime) from Lecture l")
	Double deviationLearningTimeOfLectures();

	@Query("select min(l.estimatedLearningTime) from Lecture l")
	Double minimumLearningTimeOfLectures();

	@Query("select max(l.estimatedLearningTime) from Lecture l")
	Double maximumLearningTimeOfLectures();

	@Query("select avg(l.estimatedLearningTime) from Lecture l inner join LectureCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = 1")
	Double averageLearningTimeOfCourses();

	@Query("select stdev(l.estimatedLearningTime) from Lecture l inner join LectureCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = 1")
	Double deviationLearningTimeOfCourses();

	@Query("select min(l.estimatedLearningTime) from Lecture l inner join LectureCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = 1")
	Double minimumLearningTimeOfCourses();

	@Query("select max(l.estimatedLearningTime) from Lecture l inner join LectureCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = 1")
	Double maximumLearningTimeOfCourses();
}
