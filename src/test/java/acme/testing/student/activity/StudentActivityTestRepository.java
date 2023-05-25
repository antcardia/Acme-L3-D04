
package acme.testing.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.enrolment.Activity;
import acme.framework.repositories.AbstractRepository;

public interface StudentActivityTestRepository extends AbstractRepository {

	@Query("select a from Activity a inner join Enrolment e on a.enrolment = e where e.student.userAccount.username = :username")
	Collection<Activity> findManyActivitiesByStudentUsername(String username);
}
