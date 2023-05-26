
package acme.entities.practicum;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	protected String			abstract$;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	// Esperaremos a las restricciones custom para decir al sistema que
	// teniendo las dos fechas haga un cálculo del tiempo total, y que dure una semana como mínimo
	protected Date				startTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishTime;

	@URL
	protected String			furtherInformation;

	private boolean				additional;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Practicum			practicum;

}
