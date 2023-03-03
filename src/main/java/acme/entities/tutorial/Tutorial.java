
package acme.entities.tutorial;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;

public class Tutorial extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank(message = "A code must be specified")
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}", message = "Only images of type JPEG or GIF are supported.")
	@Column(unique = true)
	protected String			code;

	@NotBlank(message = "A title must be specified")
	@Size(max = 76, message = "The title must be shorter than 76 characters")
	protected String			title;

	@NotBlank(message = "A summary must be specified")
	@Size(max = 101, message = "The title must be shorter than 101 characters")
	protected String			summary;

	@NotBlank(message = "A summary must be specified")
	@Size(max = 101, message = "The goals must be shorter than 101 characters")
	protected String			goals;

	protected Double			estimatedTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
