
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);
	@Query("select sp.practicum from SessionPracticum sp where sp.id = :id")
	Practicum findOnePracticumBySessionPracticumId(int id);
	@Query("select sp from SessionPracticum sp where sp.id = :id")
	SessionPracticum findOneSessionPracticumById(int id);
	@Query("select sp from SessionPracticum sp where sp.practicum.id = :masterId")
	Collection<SessionPracticum> findManyPracticumSessionsByMasterId(int masterId);

	@Query("select sp from SessionPracticum sp where sp.practicum.id = :masterId AND sp.isAddendum = 1")
	SessionPracticum findOneAddendumSessionByPracticumId(int masterId);

}
