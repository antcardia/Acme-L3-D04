
package acme.testing.auditor.auditRecord;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditRecord;
import acme.testing.TestHarness;

public class AuditorAuditRecordDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int masterIndex, final int recordIndex, final String subject, final String nextSubject) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Audit Records");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, subject);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");
		super.checkColumnHasValue(recordIndex, 0, nextSubject);
		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/delete-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String code) {

		final List<AuditRecord> records = this.repository.findAllUpdatableRecordsByAuditCode(code);

		for (final AuditRecord record : records) {
			final String param = "id=" + record.getId();
			final String url = "/auditor/audit-record/delete";

			super.checkLinkExists("Sign in");
			super.request(url, param);
			super.checkPanicExists();

			super.checkLinkExists("Sign in");
			super.signIn("administrator", "administrator");
			super.request(url, param);
			super.checkPanicExists();
			super.signOut();

			super.checkLinkExists("Sign in");
			super.signIn("student1", "student1");
			super.request(url, param);
			super.checkPanicExists();
			super.signOut();

			super.checkLinkExists("Sign in");
			super.signIn("company1", "company1");
			super.request(url, param);
			super.checkPanicExists();
			super.signOut();

			super.checkLinkExists("Sign in");
			super.signIn("lecturer1", "lecturer1");
			super.request(url, param);
			super.checkPanicExists();
			super.signOut();

			super.checkLinkExists("Sign in");
			super.signIn("assistant1", "assistant1");
			super.request(url, param);
			super.checkPanicExists();
			super.signOut();

			super.checkLinkExists("Sign in");
			super.signIn("auditor1", "auditor1");
			super.request(url, param);
			super.checkPanicExists();
			super.signOut();

		}
	}
}
