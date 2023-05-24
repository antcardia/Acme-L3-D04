
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goals, final String estimatedTime, final String course) {
		// HINT: this test authenticates as an assistant and then lists his or her
		// HINT: sessions, creates a new one, and check that it's been created properly.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String summary, final String goals, final String estimatedTime, final String course) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial t : tutorials) {
			param = String.format("id=%d", t.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/employer/job/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/activity/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
