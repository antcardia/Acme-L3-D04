
package acme.datatypes;

public enum Mark {

	APLUS("A+"), A("A"), B("B"), C("C"), D("D"), F("F"), FMINUS("F-");


	private final String label;


	private Mark(final String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return this.label;
	}
}
