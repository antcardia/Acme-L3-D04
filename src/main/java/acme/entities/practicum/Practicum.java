
package acme.entities.practicum;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@Min(1)
	// Esperaremos a las restricciones custom para decir al sistema que
	// haga un cálculo del tiempo total con todas las sesiones +/- 10%
	protected Integer			estimatedTotalTime;

	@NotNull
	protected boolean			draftMode;

	// Derived attributes ----------------------------------------------------


	public Double estimatedTime(final Collection<SessionPracticum> sesiones) {
		double estimatedTime = 0;
		if (sesiones.size() > 0)
			for (final SessionPracticum session : sesiones) {
				final long diffMs = session.getFinishTime().getTime() - session.getStartTime().getTime();
				final double diffH = diffMs / (1000.0 * 60 * 60);
				estimatedTime = estimatedTime + diffH;
			}
		return estimatedTime;
	}

	// Relationships ---------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Course	course;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company	company;

}
