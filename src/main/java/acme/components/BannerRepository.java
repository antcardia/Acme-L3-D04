
package acme.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b")
	int countBanners();

	@Query("select b from Banner b")
	List<Banner> findManyBanners();

	default Banner findRandomBanner() {

		final List<Banner> banners = this.findManyBanners();
		final List<Banner> options = new ArrayList<Banner>();
		final Date now = new Date();
		int counter = 0;
		Banner result = new Banner();

		for (final Banner b : banners)
			if (b.getLinkPicture() != null && b.getStartPeriod().compareTo(now) <= 0 && b.getEndPeriod().compareTo(now) >= 0) {
				options.add(b);
				counter++;
			}

		final int randomNum = ThreadLocalRandom.current().nextInt(0, counter);
		result = options.get(randomNum);
		return result;
	}

}
