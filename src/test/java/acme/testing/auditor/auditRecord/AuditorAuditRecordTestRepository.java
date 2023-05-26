
package acme.testing.auditor.auditRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditRecordTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.code = :code")
	Audit findAuditByCode(String code);

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	List<Audit> findAllAuditsByAuditor(String username);

	@Query("select r from AuditRecord r where r.audit.code = :code")
	List<AuditRecord> findAllRecordsByAuditCode(String code);

	@Query("select r from AuditRecord r where r.audit.code = :code and r.draftMode = true")
	List<AuditRecord> findAllUpdatableRecordsByAuditCode(String code);

}
