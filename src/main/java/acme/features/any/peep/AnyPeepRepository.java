
package acme.features.any.peep;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.peeps.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p")
	List<Peep> findAllPeep();

	@Query("select p from Peep p where p.id = ?1")
	Peep findPeepById(Integer id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);
}
