
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerCoursePublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerCourseTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title) {

		//A la hora de publicar, compruebo que el
		//curso todavía no esté publicado y escojo
		//un curso que ya tiene las clases publicadas
		//para poder publicar el curso. Luego compruebo
		//que el curso está publicado

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.clickOnMenu("Lecturer", "My courses");
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
		//No se ha considerado ningún caso negativo
	}

	@Test
	public void test300Hacking() {

		//Pruebo que otro rol distinto al profesor
		//no pueda publicar un curso

		Collection<Course> courses;
		String params;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (course.isDraftMode()) {
				params = String.format("id=%d", course.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		//Pruebo que no se pueda publicar un curso ya
		//publicado previamente

		Collection<Course> courses;
		String params;

		super.signIn("lecturer1", "lecturer1");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (!course.isDraftMode()) {
				params = String.format("id=%d", course.getId());
				super.request("/lecturer/course/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		//Pruebo que un profesor no pueda publicar un
		//curso que no es suyo

		Collection<Course> courses;
		String params;

		super.signIn("lecturer2", "lecturer2");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			params = String.format("id=%d", course.getId());
			super.request("/lecturer/course/publish", params);
		}
		super.signOut();
	}

}
