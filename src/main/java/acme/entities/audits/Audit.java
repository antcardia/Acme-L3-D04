
package acme.entities.audits;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.datatypes.Mark;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne
	protected Auditor			auditor;

	@Valid
	@OneToMany(mappedBy = "audit")
	protected List<AuditRecord>	auditRecords;


	// Derived attributes -----------------------------------------------------
	@Transient
	protected Mark computedMark() {
		final List<Mark> marks = new ArrayList<>();
		for (final AuditRecord aR : this.auditRecords)
			marks.add(aR.getMark());
		final Map<Mark, Integer> countMap = new EnumMap<>(Mark.class);
		Integer maxCount = 0;
		Mark mode = null;

		for (final Mark mark : marks) {
			final Integer count = countMap.getOrDefault(mark, 0) + 1;
			countMap.put(mark, count);

			if (count > maxCount) {
				maxCount = count;
				mode = mark;
			}
		}
		return mode;
	}

}
