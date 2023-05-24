
package acme.testing.assistant.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantSessionListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String sessionType) {

		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "My sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, sessionType);

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/assistant/session/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/session/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/session/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
