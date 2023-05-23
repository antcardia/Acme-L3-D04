
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentPublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected StudentEnrolmentTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String holderName, final String creditCard, final String expirationDate, final String securityCode) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnButton("Create Payment");
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("creditCard", creditCard);
		super.fillInputBoxIn("expirationDate", expirationDate);
		super.fillInputBoxIn("securityCode", securityCode);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "false");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String holderName, final String creditCard, final String expirationDate, final String securityCode) {
		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnButton("Create Payment");
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("creditCard", creditCard);
		super.fillInputBoxIn("expirationDate", expirationDate);
		super.fillInputBoxIn("securityCode", securityCode);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				params = String.format("id=%d", enrolment.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (!enrolment.isDraftMode()) {
				params = String.format("id=%d", enrolment.getId());
				super.request("/student/enrolment/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("lecturer2", "lecturer2");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			params = String.format("id=%d", enrolment.getId());
			super.request("/student/enrolment/publish", params);
		}
		super.signOut();
	}

}
