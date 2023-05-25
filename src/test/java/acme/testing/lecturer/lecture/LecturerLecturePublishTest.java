
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLecturePublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title) {

		//A la hora de publicar, compruebo que la
		//clase todavía no esté publicada y luego compruebo
		//que la clase está publicada con éxito

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "false");

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//No se ha considerado caso negativo
	}

	@Test
	public void test300Hacking() {

		//Pruebo que otro rol distinto al profesor
		//no pueda publicar una clase

		Collection<Lecture> lectures;
		String params;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				params = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		//Pruebo que no se pueda publicar una clase ya
		//publicada previamente

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer1", "lecturer1");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (!lecture.isDraftMode()) {
				params = String.format("id=%d", lecture.getId());
				super.request("/lecturer/lecture/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		//Pruebo que un profesor no pueda publicar una
		//clase que no es suya

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer2", "lecturer2");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures) {
			params = String.format("id=%d", lecture.getId());
			super.request("/lecturer/lecture/publish", params);
		}
		super.signOut();
	}

}
