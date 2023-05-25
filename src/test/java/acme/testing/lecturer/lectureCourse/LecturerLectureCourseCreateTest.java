
package acme.testing.lecturer.lectureCourse;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureCourseCreateTest extends TestHarness {

	@Autowired
	protected LecturerLectureCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lectureCourse/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		//Escojo una clase y le añado un curso que
		//todavía no tiene vinculado y compruebo que
		//el curso tiene la nueva clase añadida

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Add course");
		super.checkFormExists();

		super.fillInputBoxIn("course", code);

		super.clickOnSubmit("Add course");
		super.checkNotErrorsExist();

		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(3);
		super.clickOnButton("Lectures");

		super.checkListingExists();
		super.checkNotListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lectureCourse/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {

		//Compruebo que el curso al que quiero añadir
		//la clase me produzca un error esperado

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Add course");
		super.checkFormExists();

		super.fillInputBoxIn("course", code);

		super.clickOnSubmit("Add course");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		//Pruebo que no pueda añadir una clase
		//de un curso de otro profesor o de cualquier otro
		//rol

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				param = String.format("lectureId=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture-course/create", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture-course/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/lecturer/lecture-course/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/lecture-course/create", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
