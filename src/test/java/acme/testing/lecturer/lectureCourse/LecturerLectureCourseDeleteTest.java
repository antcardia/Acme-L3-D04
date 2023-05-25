
package acme.testing.lecturer.lectureCourse;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureCourseDeleteTest extends TestHarness {

	@Autowired
	protected LecturerLectureCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lectureCourse/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		//Escojo una clase y le borro un curso que
		//tiene vinculado y compruebo que
		//el curso no contiene la clase borrada

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Delete from course");
		super.checkFormExists();

		super.fillInputBoxIn("course", code);

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(2);
		super.clickOnButton("Lectures");

		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lectureCourse/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {

		//Compruebo que el curso al que quiero borrar
		//la clase me produzca un error esperado

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Delete from course");
		super.checkFormExists();

		super.fillInputBoxIn("course", code);

		super.clickOnSubmit("Delete");
		super.checkErrorsExist();
	}

	@Test
	public void test300Hacking() {

		//Pruebo que no pueda borrar una clase
		//de un curso de otro profesor o de cualquier otro
		//rol

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				param = String.format("lectureId=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture-course/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture-course/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/lecturer/lecture-course/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/lecture-course/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
