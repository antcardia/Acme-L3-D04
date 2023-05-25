
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startPeriod;

	//Las siguientes dos propiedades representan un periodo de tiempo
	//siendo la primera el momento de comienzo y la segunda, el momento
	//de finalización. En un futuro, se implentarán restricciones custom
	//para implementar la validación y que la fecha final no pueda ser 
	//después que la inicial
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				endPeriod;

	@URL
	@Length(max = 255)
	protected String			linkPicture;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@URL
	@Length(max = 255)
	protected String			linkWebDocument;

}
