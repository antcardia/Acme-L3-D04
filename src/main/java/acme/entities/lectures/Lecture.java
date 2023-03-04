
package acme.entities.lectures;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@Min(1)
	protected Integer			estimatedLearningTime;

	@NotBlank
	@Length(max = 100)
	protected String			body;

	protected Nature			nature;

	@URL
	protected String			furtherInformation;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
