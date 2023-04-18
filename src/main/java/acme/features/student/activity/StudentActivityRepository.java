
package acme.features.student.activity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id = :id")
	Activity findActivityById(int id);

	@Query("select a from Activity a")
	List<Activity> findAllActivity();

	@Query("select e from Enrolment e where e.draftMode = false")
	Collection<Enrolment> findAllEnrolment();

	@Query("select a from Activity a where a.id = :id and a.enrolment.draftMode = false")
	Activity findActivityByIdFinalised(int id);

	@Query("select a from Activity a inner join Enrolment e on a.enrolment = e inner join Student s on a.enrolment.student = s where a.enrolment.student.id = :studentId")
	List<Activity> findActivityByStudentId(Integer studentId);

	@Query("select e from Enrolment e inner join Activity a on a.enrolment = e where a.id = :activityId")
	Enrolment findEnrolmentByActivityId(Integer activityId);

	@Query("select e from Enrolment e where e.id = :enrolmentId")
	Enrolment findEnrolmentById(Integer enrolmentId);

	@Query("select s from Student s where s.id = :studentId")
	Student findStudentById(Integer studentId);

}
