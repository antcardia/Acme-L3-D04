/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.administratorDashboard;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.datatypes.Money;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorAdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(ua) from UserAccount ua inner join Administrator a on a member of ua.userRoles")
	Optional<Integer> countAdministratorPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Assistant a on a member of ua.userRoles")
	Optional<Integer> countAssistantPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Auditor a on a member of ua.userRoles")
	Optional<Integer> countAuditorPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Company c on c member of ua.userRoles")
	Optional<Integer> countCompanyPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Consumer c on c member of ua.userRoles")
	Optional<Integer> countConsumerPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Lecturer l on l member of ua.userRoles")
	Optional<Integer> countLecturerPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Provider p on p member of ua.userRoles")
	Optional<Integer> countProviderPrincipal();

	@Query("select count(ua) from UserAccount ua inner join Student s on s member of ua.userRoles")
	Optional<Integer> countStudentPrincipal();

	@Query("select count(p) from Peep p")
	Optional<Integer> countAllPeeps();

	@Query("select count(p) from Peep p where p.email != null and p.link != null")
	Optional<Integer> countAllPeepsWithEmailAddressAndLink();

	@Query("select count(b) from Bulletin b")
	Optional<Integer> countAllBulletins();

	@Query("select count(b) from Bulletin b where b.flag = true")
	Optional<Integer> countAllCriticalBulletins();

	@Query("select count(b) from Bulletin b where b.flag = false")
	Optional<Integer> countAllNonCriticalBulletins();

	@Query("select o.price from Offer o")
	Collection<Money> findAllPricesOfOffers();

	@Query("select n from Note n where n.moment >= :deadline")
	Collection<Note> findAllNotes(Date deadline);

	@Query("select a from Administrator a where a.userAccount.id = :id")
	Administrator findOneAdministratorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
}
