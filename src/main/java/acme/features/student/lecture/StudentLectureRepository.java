
package acme.features.student.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l inner join LectureCourse lc on lc.lecture = l inner join Course c on  c = lc.course where lc.course.id = :courseId")
	List<Lecture> findAllLectureByCourseId(Integer courseId);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

}
