
package acme.entities.peeps;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
public class Peep extends AbstractEntity {

	@NotNull
	@Past
	protected Date		instantiation;

	@NotEmpty
	@Length(max = 76)
	protected String	title;

	@NotEmpty
	@Length(max = 76)
	protected String	nick;

	@NotEmpty
	@Length(max = 100)
	protected String	message;

	@Email
	protected String	email;

	@URL
	protected String	link;
}
