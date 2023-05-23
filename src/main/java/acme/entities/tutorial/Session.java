
package acme.entities.tutorial;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
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

	@NotBlank
	@Size(max = 75)
	protected String			title;

	@NotBlank
	@Size(max = 100)
	protected String			summary;

	@NotNull
	protected Nature			sessionType;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				start;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	// Esperaremos a las restricciones custom para decir al sistema que
	// la la fecha final va después de la fecha de inicio, asía como algunas
	// otras restricciones
	protected Date				end;

	@URL
	@Length(max = 255)
	protected String			furtherInformation;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Tutorial			tutorial;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
