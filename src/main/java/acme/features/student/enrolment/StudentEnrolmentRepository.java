
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("select e from Enrolment e where e.student.id = :idStudent")
	List<Enrolment> findAllEnrolmentByStudentId(Integer idStudent);

	@Query("select c from Course c inner join Enrolment e on e.course.id = c.id inner join Student s on e.student.id = s.id where s.id = :id")
	Course findCourseByStudentId(int id);

	@Query("select s from Student s where s.id = :activeRoleId")
	Student findOneStudentById(int activeRoleId);

	@Query("select e from Enrolment e where e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

	@Query("select c from Course c inner join Enrolment e on e.course = c where e.id = :id")
	Course findCourseByEnrolmentId(Integer id);

	@Query("select c from Course c where c.draftMode = false")
	List<Course> findAllCourse();

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	List<Activity> findActivitiesByEnrolmentId(Integer enrolmentId);
}
