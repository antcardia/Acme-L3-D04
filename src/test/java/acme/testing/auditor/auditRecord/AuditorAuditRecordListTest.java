
package acme.testing.auditor.auditRecord;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditRecordListTest extends TestHarness {

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int masterIndex, final int recordIndex, final String subject, final String mark, final String hours, final String published) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(masterIndex);
		super.clickOnButton("Audit Records");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.checkColumnHasValue(recordIndex, 2, hours);
		super.checkColumnHasValue(recordIndex, 3, published);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		final List<Audit> audits = this.repository.findAllAuditsByAuditor("auditor2");

		for (final Audit audit : audits) {
			final String param = "auditId=" + audit.getId();
			final String url = "/auditor/audit-record/list";

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
