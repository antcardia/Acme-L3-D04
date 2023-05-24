
package acme.testing.assistant.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AssistantSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String reference, final String contractor, final String title, final String deadline, final String salary, final String score, final String moreInfo, final String description) {
		// HINT: this test logs in as an employer, lists his or her jobs, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("employer1", "employer1");

		super.clickOnMenu("Employer", "List my jobs");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, reference);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("reference", reference);
		super.fillInputBoxIn("contractor", contractor);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("deadline", deadline);
		super.fillInputBoxIn("salary", salary);
		super.fillInputBoxIn("score", score);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("description", description);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, reference);
		super.checkColumnHasValue(recordIndex, 1, deadline);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("reference", reference);
		super.checkInputBoxHasValue("contractor", contractor);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("deadline", deadline);
		super.checkInputBoxHasValue("salary", salary);
		super.checkInputBoxHasValue("score", score);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("description", description);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String reference, final String contractor, final String title, final String deadline, final String salary, final String score, final String moreInfo, final String description) {
		// HINT: this test attempts to update a job with wrong data.

		super.signIn("employer1", "employer1");

		super.clickOnMenu("Employer", "List my jobs");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, reference);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("reference", reference);
		super.fillInputBoxIn("contractor", contractor);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("deadline", deadline);
		super.fillInputBoxIn("salary", salary);
		super.fillInputBoxIn("score", score);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("description", description);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a job with a role other than "Employer",
		// HINT+ or using an employer who is not the owner.

		//	Collection<Job>	jobs;
		final String param;

		//	jobs = this.repository.findManyJobsByEmployerUsername("employer1");
		//for (final Job job : jobs) {
		//	param = String.format("id=%d", job.getId());

		//			super.checkLinkExists("Sign in");
		//			super.request("/employer/job/update", param);
		//			super.checkPanicExists();
		//
		//			super.signIn("administrator", "administrator");
		//			super.request("/employer/job/update", param);
		//			super.checkPanicExists();
		//			super.signOut();
		//
		//			super.signIn("employer2", "employer2");
		//			super.request("/employer/job/update", param);
		//			super.checkPanicExists();
		//			super.signOut();
		//
		//			super.signIn("worker1", "worker1");
		//			super.request("/employer/job/update", param);
		//			super.checkPanicExists();
		//			super.signOut();
	}
}

//}
