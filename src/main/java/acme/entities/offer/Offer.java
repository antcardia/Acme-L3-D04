
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
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

	@Past
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String			heading;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	protected Date				startDay;

	protected Date				lastDay;

	protected Money				price;

	@URL
	protected String			link;

}
