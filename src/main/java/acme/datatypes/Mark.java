
package acme.datatypes;

import javax.validation.constraints.NotNull;

public enum Mark {

	APLUS, A, B, C, D, F, FMINUS;


	@NotNull
	public static Mark parse(final String mark) {
		Mark res = null;
		if (mark.trim().equals("APLUS"))
			res = APLUS;
		else if (mark.trim().equals("A"))
			res = A;
		else if (mark.trim().equals("B"))
			res = B;
		else if (mark.trim().equals("C"))
			res = C;
		else if (mark.trim().equals("D"))
			res = D;
		else if (mark.trim().equals("F"))
			res = F;
		else if (mark.trim().equals("FMINUS"))
			res = FMINUS;
		return res;
	}

}
