
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select a from Activity a inner join Enrolment e on a.enrolment = e where e.student.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(String username);
}
