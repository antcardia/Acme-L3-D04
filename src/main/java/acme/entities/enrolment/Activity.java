
package acme.entities.enrolment;

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

import acme.datatypes.Nature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			tittle;

	@NotBlank
	@Length(max = 75)
	protected String			workbookName;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotNull
	protected Nature			atype;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	// Validacion de que la fecha de inicio sea anterior a la del final
	protected Date				startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	// Validacion de que la fecha de inicio sea anterior a la del final
	protected Date				finishTime;

	@URL
	protected String			link;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Enrolment			enrolment;

}
