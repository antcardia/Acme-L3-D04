
package acme.features.administrator.banner;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.entities.system.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from Banner b where b.id = :masterId")
	Banner findBannerById(int masterId);

	@Query("select b from Banner b")
	List<Banner> findAllBanners();

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

}
