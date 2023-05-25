
package acme.features.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.system.SystemConfiguration;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	@Query("select s from Session s where s.id = :id")
	Session findOneSessionById(int id);

	@Query("select a from Assistant a where a.id = :id")
	Assistant findOneAssistantById(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findOneTutorialByCode(String code);

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllTutorial();

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findManyTutorialsByAssistantId(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select s from Session s")
	Collection<Session> findAllSession();

	@Query("select s from Session s where s.tutorial.id =:id")
	Collection<Session> findManySessionsByTutorialId(int id);

	@Query("select s from Session s where s.tutorial.assistant.id = :id")
	Collection<Session> findManySessionsByAssistantId(int id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
