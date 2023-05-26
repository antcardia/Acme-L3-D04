
package acme.testing.auditor.auditRecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditRecordCorrectionTest extends TestHarness {

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/correction-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int masterIndex, final int recordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String link, final String mark, final String confirm) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Add a correction Audit Record");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("periodStart", startDate);
		super.fillInputBoxIn("periodEnd", finishDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("confirm", confirm);
		super.clickOnSubmit("Create Correction");

		super.clickOnButton("Audit Records");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 3, "true");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/correction-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int masterIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String link, final String mark, final String confirm) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Add a correction Audit Record");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("periodStart", startDate);
		super.fillInputBoxIn("periodEnd", finishDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("confirm", confirm);
		super.clickOnSubmit("Create Correction");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test201Negative() {

		super.signIn("auditor2", "auditor2");

		final Audit audit = this.repository.findAuditByCode("ABC123");
		final String param = "auditId=" + audit.getId();
		final String url = "/auditor/audit-record/correct";

		super.request(url, param);
		super.checkPanicExists();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/correction-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String code) {

		final Audit audit = this.repository.findAuditByCode(code);

		final String param = "auditId=" + audit.getId();
		final String url = "/auditor/audit-record/correct";

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
