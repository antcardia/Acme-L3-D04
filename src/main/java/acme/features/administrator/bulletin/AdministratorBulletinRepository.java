
package acme.features.administrator.bulletin;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBulletinRepository extends AbstractRepository {

	@Query("select o from Bulletin o")
	List<Bulletin> findAllBulletin();

	@Query("select o from Bulletin o where o.id = ?1")
	Bulletin findBulletinById(Integer id);
}
