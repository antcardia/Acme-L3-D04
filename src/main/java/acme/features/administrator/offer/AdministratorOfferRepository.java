
package acme.features.administrator.offer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Offer;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select o from Offer o")
	List<Offer> findAllOffer();

	@Query("select o from Offer o where o.id = ?1")
	Offer findOfferById(Integer id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
