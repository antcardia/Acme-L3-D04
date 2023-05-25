
package acme.testing.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

public interface StudentEnrolmentTestRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.student.userAccount.username = :username")
	Collection<Enrolment> findManyEnrolmentsByStudentUsername(String username);
}
