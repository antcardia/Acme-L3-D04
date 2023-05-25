
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureListTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String estimatedLearningTime, final String type) {

		//Compruebo todos los campos de la lista 
		//de clases de un curso

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, estimatedLearningTime);
		super.checkColumnHasValue(recordIndex, 2, type);
		super.signOut();
	}

	@Test
	public void test200Negative() {
		//No existe caso negativo en listado
	}

	@Test
	public void test300Hacking() {

		//Pruebo que no pueda acceder a la lista de sus clases
		//de su curso cualquier otro rol diferente al profesor

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				param = String.format("masterId=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/lecture/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
