
package acme.roles;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.audits.Audit;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			firm;

	@NotBlank
	@Length(max = 25)
	protected Integer			professionalId;

	@NotBlank
	@Length(max = 100)
	protected String			certificates;

	@URL
	protected String			furtherInformation;

	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	protected List<Audit>		audits;
}
