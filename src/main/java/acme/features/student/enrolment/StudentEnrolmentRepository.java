
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.student.id = :id ")
	List<Enrolment> findAllEnrolmentByStudentId(Integer id);
}
