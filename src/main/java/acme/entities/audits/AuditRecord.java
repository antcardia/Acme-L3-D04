
package acme.entities.audits;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Mark;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assessment;

	//La validez del periodo temporal deberá comprobarse con un validador complejo en el futuro ya que por ahora no se ha dado en clase
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				periodStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				periodEnd;

	@NotNull
	protected Mark				mark;

	@URL
	@Length(max = 254)
	protected String			link;

	@NotNull
	protected Boolean			draftMode;

	@NotNull
	protected Boolean			correction;

	//Derviced atributes


	@Transient
	public Double getHoursFromPeriod() {
		final Duration duration = MomentHelper.computeDuration(this.periodStart, this.periodEnd);
		return duration.getSeconds() / 3600.0;
	}


	// Relationships ----------------------------------------------------------
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Audit audit;
}
