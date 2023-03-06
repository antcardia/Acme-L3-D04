
package acme.entities.audits;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecords extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			subject;
	@NotBlank
	@Length(max = 100)
	protected String			assesment;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				periodStart;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				periodEnd;

	protected Mark				mark;
	@URL
	protected String			link;
}
