
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String retailPrice) {

		//Listamos todos los cursos del profesor y 
		//comprobamos que los campos encontrados
		//son los esperados

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, retailPrice);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//No hay caso negativo en un listado
	}

	@Test
	public void test300Hacking() {

		//Pruebo que un rol diferente al profesor
		//no pueda listar sus cursos

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
