
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerLectureListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String estimatedLearningTime, final String type) {

		//Comprueba todos los campos de la lista de clases

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
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
		//cualquier otro rol diferente al profesor

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
