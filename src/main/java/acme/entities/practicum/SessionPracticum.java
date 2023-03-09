
package acme.entities.practicum;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SessionPracticum extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotNull
	protected Date				startTime;

	@NotNull
	protected Date				finishTime;

	@URL
	protected String			furtherInformation;

	// Derived attributes -----------------------------------------------------


	protected Long durationInHours() {
		final long durationInMillis = this.finishTime.getTime() - this.startTime.getTime();
		return TimeUnit.MILLISECONDS.toHours(durationInMillis);
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Practicum practicum;

}
