
package acme.features.student.activity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id = :id")
	Activity findActivityById(int id);

	@Query("select a from Activity a")
	List<Activity> findAllActivity();

	@Query("select e from Enrolment e where e.draftMode = false and e.student.id = :studentId")
	Collection<Enrolment> findAllEnrolmentByStudentId(Integer studentId);

	@Query("select a from Activity a where a.id = :id and a.enrolment.draftMode = false")
	Activity findActivityByIdFinalised(int id);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	List<Activity> findActivitiesByEnrolmentId(Integer enrolmentId);

	@Query("select e from Enrolment e inner join Activity a on a.enrolment = e where a.id = :activityId")
	Enrolment findEnrolmentByActivityId(Integer activityId);

	@Query("select e from Enrolment e where e.id = :enrolmentId and e.draftMode = false")
	Enrolment findEnrolmentById(Integer enrolmentId);

	@Query("select s from Student s where s.id = :studentId")
	Student findStudentById(Integer studentId);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select e from Enrolment e where e.student.id = :studentId")
	Enrolment findEnrolmentByStudentId(Integer studentId);

}
