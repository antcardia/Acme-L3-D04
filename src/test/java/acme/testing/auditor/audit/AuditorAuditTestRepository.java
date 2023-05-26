
package acme.testing.auditor.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.code = :code")
	Audit findAuditByCode(String code);

	@Query("select ar from AuditRecord ar where ar.audit.code = :code")
	List<AuditRecord> findAllAuditingRecords(String code);

}
