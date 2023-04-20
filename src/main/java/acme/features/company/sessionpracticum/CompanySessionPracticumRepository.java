
package acme.features.company.sessionpracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = ?1")
	Practicum findOnePracticumById(int practicumId);

	@Query("select c from Practicum c")
	Collection<Practicum> findManyPracticums();

	@Query("select s from SessionPracticum s where s.id = ?1")
	SessionPracticum findOneSessionPracticumById(int sessionPracticumId);

	@Query("select s from SessionPracticum s inner join Practicum p on s.practicum.id = p.id where p.company.id = ?1")
	Collection<SessionPracticum> findManySessionPracticumsByCompanyId(int companyId);

	@Query("select sp.practicum from SessionPracticum sp where sp.id = ?1")
	Practicum findOnePracticumBySessionPracticumId(int sessionPracticumId);

	@Query("select sp from SessionPracticum sp where sp.practicum.id != ?1 and sp.draftMode = false")
	Collection<SessionPracticum> findManySessionPracticumsByExtraAvailableAndPracticumId(int id);
}
