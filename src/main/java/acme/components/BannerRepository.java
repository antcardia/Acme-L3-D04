/*
 * AdvertisementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import java.util.ArrayList;
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
		int counter = 0;
		Banner result = new Banner();

		for (final Banner b : banners)
			if (b.getLinkPicture() != null) {
				options.add(b);
				counter++;
			}

		final int randomNum = ThreadLocalRandom.current().nextInt(0, counter);
		System.out.println(counter);
		System.out.println(randomNum);
		result = options.get(randomNum);
		return result;
	}

}
