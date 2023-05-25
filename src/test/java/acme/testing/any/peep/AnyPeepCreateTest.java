
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String nick, final String message, final String email, final String link) {

		//Creo un nuevo comentario y compruebo que se
		//ha creado con éxito

		super.checkLinkExists("Sign in");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkNotErrorsExist();

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, message);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String nick, final String message, final String email, final String link) {

		//Compruebo que a la hora de crear un nuevo
		//comentario se produzcan errores esperados

		super.checkLinkExists("Sign in");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
	}

	@Test
	public void test300Hacking() {
		//No hay caso hacking
	}
}
