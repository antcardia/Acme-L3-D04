
package acme.features.company.sessionpracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionPracticumRepository extends AbstractRepository {

	@Query("SELECT s FROM Practicum s WHERE s.id= :id")
	Practicum findOnePracticumById(int id);

	@Query("SELECT s FROM SessionPracticum s WHERE s.practicum.id = :id")
	Collection<SessionPracticum> findManyPracticumSessionsByPracticumId(int id);

	@Query("SELECT s FROM SessionPracticum s WHERE s.id= :id")
	SessionPracticum findOnePracticumSessionById(int id);

	@Query("select s from SessionPracticum s inner join Practicum p on s.practicum.id = p.id where p.company.id = ?1")
	Collection<SessionPracticum> findManySessionPracticumsByCompanyId(int companyId);

	@Query("select ps from SessionPracticum ps where ps.practicum.id = :masterId AND ps.additional = 1")
	SessionPracticum findOneAddendumSessionByPracticumId(int masterId);
}
