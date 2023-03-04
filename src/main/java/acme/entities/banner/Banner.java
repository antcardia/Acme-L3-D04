
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endPeriod;

	@URL
	protected String			linkPicture;

	@NotBlank
	@Length(max = 76)
	protected String			slogan;

	@URL
	protected String			linkWebDocument;

}
