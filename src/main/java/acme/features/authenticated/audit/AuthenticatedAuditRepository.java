
package acme.features.authenticated.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Mark;
import acme.entities.audits.Audit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select audit from Audit audit where audit.course.id = :courseId")
	List<Audit> findAllAuditsByCourseId(int courseId);

	@Query("select audit from Audit audit where audit.id = :id")
	Audit findAuditById(int id);

	@Query("select ar.mark from AuditRecord ar where ar.audit.id = :id")
	List<Mark> findAllMarksByAuditId(int id);

}
