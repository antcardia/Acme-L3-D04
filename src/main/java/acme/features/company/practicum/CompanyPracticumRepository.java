
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select c from Practicum c WHERE c.company.id = :id")
	Collection<Practicum> findManyPracticumsByCompanyId(int id);

	@Query("SELECT c FROM Practicum c WHERE c.id = :id")
	Practicum findPracticumById(int id);

	@Query("SELECT c FROM Company c WHERE c.id = :id")
	Company findCompanyById(int id);

	@Query("SELECT c FROM Practicum c WHERE c.code = :code")
	Practicum findPracticumByCode(String code);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("SELECT s FROM SessionPracticum s WHERE s.practicum.id = :id")
	Collection<SessionPracticum> findManySessionPracticumByPracticumId(int id);

}
