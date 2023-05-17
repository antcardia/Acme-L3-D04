
package acme.features.student.payment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Enrolment;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentPaymentRepository extends AbstractRepository {

	@Query("select o from Student o")
	List<Student> findAllStudent();

	@Query("select o from Student o where o.id = ?1")
	Student findStudentById(Integer id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select e from Enrolment e where e.id = ?1")
	Enrolment findEnrolmentById(int id);

}
