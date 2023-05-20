
package acme.testing.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseTestRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.userAccount.username = :username")
	Collection<Course> findManyCoursesByLecturerUsername(String username);

	@Query("select l from Lecture l where l.lecturer.userAccount.username = :username")
	Collection<Lecture> findManyLecturesByLecturerUsername(String username);

}
