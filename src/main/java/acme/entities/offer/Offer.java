
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String			heading;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	// validacion de que empiece un dia despues de que se instancie la oferta
	protected Date				startDay;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	// validacion debe durar al menos una semana
	protected Date				lastDay;

	@NotNull
	protected Money				price;

	@URL
	@Length(max = 255)
	protected String			link;

}
