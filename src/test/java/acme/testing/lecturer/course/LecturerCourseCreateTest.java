
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$, final String retailPrice, final String furtherInformation) {

		//Creamos un curso, comprobamos que los campos
		//sean los rellenados anteriormente y que la
		//lista de clases esté vacía

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("furtherInformation", furtherInformation);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, retailPrice);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("furtherInformation", furtherInformation);
		super.clickOnButton("Lectures");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abstract$, final String retailPrice, final String furtherInformation) {

		//Intentamos crear un curso que provoque
		//algún error esperado en cualquier campo

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("furtherInformation", furtherInformation);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		//Probamos que no se pueda crear un curso
		//desde un rol que no sea profesor

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();
	}

}
