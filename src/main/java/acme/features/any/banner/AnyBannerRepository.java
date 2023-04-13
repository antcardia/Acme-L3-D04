
package acme.features.any.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyBannerRepository extends AbstractRepository {

	@Query("select b from Banner b where b.id = :masterId")
	Banner findOneBannerById(int masterId);

	@Query("select b from Banner b")
	Collection<Banner> findAllBanners();

}
