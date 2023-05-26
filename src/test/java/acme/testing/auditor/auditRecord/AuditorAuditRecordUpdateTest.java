
package acme.testing.auditor.auditRecord;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditRecord;
import acme.testing.TestHarness;

public class AuditorAuditRecordUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int masterIndex, final int recordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String link, final String mark, final int finalIndex) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Audit Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("periodStart", startDate);
		super.fillInputBoxIn("periodEnd", finishDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Update");

		super.checkColumnHasValue(finalIndex, 0, subject);
		super.checkColumnHasValue(finalIndex, 3, "true");

		super.clickOnListingRecord(finalIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("periodStart", startDate);
		super.checkInputBoxHasValue("periodEnd", finishDate);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int masterIndex, final int recordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String link, final String mark) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Audit Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("periodStart", startDate);
		super.fillInputBoxIn("periodEnd", finishDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/update-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String code) {

		final List<AuditRecord> records = this.repository.findAllUpdatableRecordsByAuditCode(code);

		for (final AuditRecord record : records) {
			final String param = "id=" + record.getId();
			final String url = "/auditor/audit-record/update";

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
