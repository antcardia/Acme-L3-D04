
package acme.features.auditor.audit_record;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditRecord;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.audit.id = :auditId")
	List<AuditRecord> findAllAuditRecordsByAuditId(int auditId);

	@Query("select a from Audit a where a.id = :auditId")
	Audit findAuditById(int auditId);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select a from Audit a where a.draftMode = false")
	List<Audit> findAllPublishedAudits();

	@Query("select s from SystemConfiguration s")
	SystemConfiguration findSystemConfiguration();
}
