
package acme.testing.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorial.Session;
import acme.framework.repositories.AbstractRepository;

public interface AssistantSessionTestRepository extends AbstractRepository {

	@Query("select a from Activity a inner join Enrolment e on a.enrolment = e where e.student.userAccount.username = :username")
	Collection<Session> findManySessionsByAssistantUsername(String username);
}
