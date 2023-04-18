
package acme.features.auditor.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Mark;
import acme.entities.audits.Audit;
import acme.entities.audits.AuditRecord;
import acme.entities.courses.Course;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.id = :auditorId")
	List<Audit> findAllAuditsByAuditorId(int auditorId);

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select ar.mark from AuditRecord ar where ar.draftMode = false and ar.audit.id = :id")
	List<Mark> findAllReleasedMarksByAuditId(int id);

	@Query("select a from Auditor a where a.userAccount.id = :accountId")
	Auditor findAuditorByAccountId(int accountId);

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select count(a)>0 from Audit a where a.code = :code and a.id != :id")
	boolean existsAuditWithCode(String code, int id);

	@Query("select c from Course c where c.draftMode = false")
	List<Course> findAllPublishedCourses();

	@Query("select ar from AuditRecord ar where ar.audit.id = :id")
	List<AuditRecord> findAllAuditRecordByAuditId(int id);

	@Query("select s from SystemConfiguration s")
	SystemConfiguration findSystemConfiguration();

}
