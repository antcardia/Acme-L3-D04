
package acme.testing.auditor.auditRecord;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditRecord;
import acme.testing.TestHarness;

public class AuditorAuditRecordShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int masterIndex, final int recordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String link, final String mark) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Audit Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("periodStart", startDate);
		super.checkInputBoxHasValue("periodEnd", finishDate);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/show-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String code) {

		final List<AuditRecord> records = this.repository.findAllRecordsByAuditCode(code);

		for (final AuditRecord record : records) {
			final String param = "id=" + record.getId();
			final String url = "/auditor/audit-record/show";

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
