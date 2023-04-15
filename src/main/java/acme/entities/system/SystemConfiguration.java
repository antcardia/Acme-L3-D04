
package acme.entities.system;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "^[A-Z]{3}$")
	@NotNull
	protected String			systemCurrency;

	@Pattern(regexp = "^([A-Z]{3},)*[A-Z]{3}$")
	@NotNull
	protected String			acceptedCurrencies;

	@Range(min = 0, max = 1)
	@NotNull
	protected Double			threshold;

	@Pattern(regexp = "^(\"[^\"]\"|[^\",]+)(,(\"[^\"]\"|[^\",]+))*$")
	@NotNull
	protected String			spamWords;
}
