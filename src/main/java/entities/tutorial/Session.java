
package entities.tutorial;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "session")
public class Session {

	@NotBlank(message = "Title must be specified")
	@Size(max = 200, message = "Title must be shorter than 76 characters")
	private String		title;

	private SessionType	sessionType;

}
