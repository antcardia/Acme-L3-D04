/*
 * AdministratorDashboardShowService.java
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

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Statistic;
import acme.entities.notes.Note;
import acme.forms.AdministratorDashboard;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorAdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorAdministratorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final AdministratorDashboard dashboard = new AdministratorDashboard();
		final Map<String, Integer> principalsByRole = new HashMap<>();
		final Integer countAdmins = this.repository.countAdministratorPrincipal().orElse(0);
		final Integer countAssistants = this.repository.countAssistantPrincipal().orElse(0);
		final Integer countAuditors = this.repository.countAuditorPrincipal().orElse(0);
		final Integer countCompanies = this.repository.countCompanyPrincipal().orElse(0);
		final Integer countConsumers = this.repository.countConsumerPrincipal().orElse(0);
		final Integer countLecturers = this.repository.countLecturerPrincipal().orElse(0);
		final Integer countProviders = this.repository.countProviderPrincipal().orElse(0);
		final Integer countStudents = this.repository.countStudentPrincipal().orElse(0);
		principalsByRole.put("Administrator", countAdmins);
		principalsByRole.put("Assistant", countAssistants);
		principalsByRole.put("Auditor", countAuditors);
		principalsByRole.put("Company", countCompanies);
		principalsByRole.put("Consumer", countConsumers);
		principalsByRole.put("Lecturer", countLecturers);
		principalsByRole.put("Provider", countProviders);
		principalsByRole.put("Student", countStudents);
		dashboard.setTotalNumberOfPrincipalsByRole(principalsByRole);

		final int countPeeps = this.repository.countAllPeeps().orElse(0);
		final int countPeepsWithEmailAndLink = this.repository.countAllPeepsWithEmailAddressAndLink().orElse(0);
		dashboard.setPeepsWithEmailAddressAndLinkRatio((double) countPeepsWithEmailAndLink / countPeeps);

		final int countBulletins = this.repository.countAllBulletins().orElse(0);
		final int countAllCriticalBulletins = this.repository.countAllCriticalBulletins().orElse(0);
		final int countAllNonCriticalBulletins = this.repository.countAllNonCriticalBulletins().orElse(0);

		dashboard.setCriticalBulletinsRatio((double) countAllCriticalBulletins / countBulletins);
		dashboard.setNonCriticalBulletinsRatio((double) countAllNonCriticalBulletins / countBulletins);

		final Collection<Money> pricesOfOffers = this.repository.findAllPricesOfOffers();

		final Map<String, List<Double>> pricesOfOffersByCurrency = new HashMap<>();
		for (final Money price : pricesOfOffers) {
			final String currency = price.getCurrency();
			final Double amount = price.getAmount();
			List<Double> list = pricesOfOffersByCurrency.get(currency);
			if (list == null) {
				list = new ArrayList<>();
				pricesOfOffersByCurrency.put(currency, list);
			} else
				list.add(amount);
		}
		final Map<String, Double> avgPricesOfOffersByCurrency = new HashMap<>();
		final Map<String, Double> devPricesOfOffersByCurrency = new HashMap<>();
		final Map<String, Double> minPricesOfOffersByCurrency = new HashMap<>();
		final Map<String, Double> maxPricesOfOffersByCurrency = new HashMap<>();
		for (final Map.Entry<String, List<Double>> entry : pricesOfOffersByCurrency.entrySet()) {
			final String currency = entry.getKey();
			final List<Double> listOfAmount = entry.getValue();

			final Double avg = Statistic.averageCalc(listOfAmount);
			final Double deviation = Statistic.deviationCalc(listOfAmount, avg);
			final Double minimum = Statistic.minimumCalc(listOfAmount);
			final Double maximum = Statistic.maximumCalc(listOfAmount);

			avgPricesOfOffersByCurrency.put(currency, avg);
			devPricesOfOffersByCurrency.put(currency, deviation);
			minPricesOfOffersByCurrency.put(currency, minimum);
			maxPricesOfOffersByCurrency.put(currency, maximum);
		}
		dashboard.setAverageBudgetByCurrency(avgPricesOfOffersByCurrency);
		dashboard.setMinBudgetByCurrency(minPricesOfOffersByCurrency);
		dashboard.setMaxBudgetByCurrency(maxPricesOfOffersByCurrency);
		dashboard.setBudgetDeviationByCurrency(devPricesOfOffersByCurrency);

		final Date deadline = MomentHelper.deltaFromCurrentMoment(-10, ChronoUnit.WEEKS);
		final Collection<Note> notes = this.repository.findAllNotes(deadline);
		final HashMap<Integer, Double> notesPerWeek = new HashMap<>();

		for (final Note note : notes) {
			final Date date = note.getMoment();
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			final int week = calendar.get(Calendar.WEEK_OF_YEAR);

			if (notesPerWeek.containsKey(week))
				notesPerWeek.put(week, notesPerWeek.get(week) + 1.0);
			else
				notesPerWeek.put(week, 1.0);
		}

		final Collection<Double> countPerWeek = notesPerWeek.values();
		final Double avgNotes = Statistic.averageCalc(countPerWeek);
		final Double devNotes = Statistic.deviationCalc(countPerWeek, avgNotes);
		final Double minNotes = Statistic.minimumCalc(countPerWeek);
		final Double maxNotes = Statistic.maximumCalc(countPerWeek);
		dashboard.setAverageNotesPosted(avgNotes);
		dashboard.setNotesPostedDeviation(devNotes);
		dashboard.setMinNotesPosted(minNotes);
		dashboard.setMaxNotesPosted(maxNotes);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"totalNumberOfPrincipalsByRole", "peepsWithEmailAddressAndLinkRatio", // 
			"criticalBulletinsRatio", "nonCriticalBulletinsRatio", //
			"averageBudgetByCurrency", "minBudgetByCurrency", // 
			"maxBudgetByCurrency", "budgetDeviationByCurrency", // 
			"averageNotesPosted", "minNotesPosted", //
			"maxNotesPosted", "notesPostedDeviation");

		super.getResponse().setData(tuple);
	}

}
