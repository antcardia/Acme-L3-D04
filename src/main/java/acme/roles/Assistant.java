
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Assistant extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			supervisor;

	@NotBlank
	@Length(max = 100)
	protected String			expertiseFields;

	@NotBlank
	@Length(max = 100)
	protected String			resume;

	@URL
	@Length(max = 255)
	protected String			furtherInformation;

}
