
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AduitorAuditCreateTest extends TestHarness {

	// Test methods -----------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String course, final String strongPoints, final String weakPoints, final String conclusion) {

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("conclusion", conclusion);

		super.clickOnSubmit("Create");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("marks", "N/A");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final String code, final String course, final String strongPoints, final String weakPoints, final String conclusion) {
		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("conclusion", conclusion);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/create");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

	}
}
