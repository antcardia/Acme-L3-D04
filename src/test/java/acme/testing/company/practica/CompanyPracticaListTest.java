
package acme.testing.company.practica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticaListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practica/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$) {
		// HINT: this test authenticates as an employer and checks that he or
		// HINT+ she can display the expected announcements.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, abstract$);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to request the list of announcements as
		// HINT+ an anonymous principal.

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/list");
		super.checkPanicExists();
	}

	// Ancillary methods ------------------------------------------------------

}
