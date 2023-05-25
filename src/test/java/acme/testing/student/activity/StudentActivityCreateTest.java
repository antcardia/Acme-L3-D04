
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;
import acme.testing.student.enrolment.StudentEnrolmentTestRepository;

public class StudentActivityCreateTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String tittle, final String abstract$, final String workbookName, final String atype, final String startTime, final String finishTime, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("Activities");

		super.clickOnButton("Create");
		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("workbookName", workbookName);
		super.fillInputBoxIn("atype", atype);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("finishTime", finishTime);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Student", "My enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("Activities");
		super.checkColumnHasValue(recordIndex, 0, tittle);
		super.checkColumnHasValue(recordIndex, 1, workbookName);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("tittle", tittle);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("workbookName", workbookName);
		super.checkInputBoxHasValue("atype", atype);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("finishTime", finishTime);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String tittle, final String abstract$, final String workbookName, final String atype, final String startTime, final String finishTime, final String link) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Activities");

		super.clickOnButton("Create");
		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("workbookName", workbookName);
		super.fillInputBoxIn("atype", atype);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("finishTime", finishTime);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment e : enrolments) {
			param = String.format("id=%d", e.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/employer/job/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/activity/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
