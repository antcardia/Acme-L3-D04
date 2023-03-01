
package entities.tutorial;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank(message = "Title must be specified")
	@Size(max = 76, message = "Title must be shorter than 76 characters")
	protected String			title;

	@NotBlank(message = "A summary must be specified")
	@Size(max = 101, message = "The summary must be shorter than 101 characters")
	protected String			summary;

	protected SessionType		sessionType;

	@Min(1)
	@Max(5)
	protected Integer			duration;

	@URL
	protected String			furtherInformation;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
