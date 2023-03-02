
package acme.entities.enrolment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

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
	@Length(max = 100)
	protected String			abstractActivity;

	protected ActivityType		atype;

	protected Date				time;

	@URL
	protected String			link;

	@ManyToOne
	protected Enrolment			enrolment;

}
