
package acme.features.company.dashboard;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("SELECT s FROM SessionPracticum s WHERE s.practicum.company.id = :id")
	Collection<SessionPracticum> findManySessionPracticumsByCompanyId(int id);

	@Query("SELECT s FROM SessionPracticum s WHERE s.practicum.id = :id")
	Collection<SessionPracticum> findManySessionPracticumsByPracticumId(int id);

	@Query("SELECT s FROM Practicum s WHERE s.company.id = :id")
	Collection<Practicum> findManyPracticumByCompanyId(int id);

	default Double getDurationInHours(final SessionPracticum session) {
		final Duration duration = MomentHelper.computeDuration(session.getStartTime(), session.getFinishTime());
		final Long seconds = duration.getSeconds();
		return seconds.doubleValue() / 3600.;
	}
	//Media de duracion de las sesiones de practica de una empresa
	default Double averageTimeOfSessionPracticumOfCompany(final int id) {
		final List<SessionPracticum> sessions = (List<SessionPracticum>) this.findManySessionPracticumsByCompanyId(id);
		if (sessions.isEmpty())
			return null;
		double ac = 0.0;
		for (final SessionPracticum ls : sessions)
			ac = ac + this.getDurationInHours(ls);

		return (double) (ac / sessions.size());
	}

	default Double deviationTimeOfSessionPracticumOfCompany(final int id) {
		final List<SessionPracticum> sessions = (List<SessionPracticum>) this.findManySessionPracticumsByCompanyId(id);
		if (sessions.size() < 2)
			return null;
		final Double mean = this.averageTimeOfSessionPracticumOfCompany(id);

		final List<Double> durationsOfSessions = new ArrayList<>();
		for (final SessionPracticum ls : sessions)
			durationsOfSessions.add(this.getDurationInHours(ls));

		final Double numerator = durationsOfSessions.stream().mapToDouble(x -> (x - mean) * (x - mean)).sum();
		return Math.sqrt(numerator / durationsOfSessions.size());
	}

	default Double minimumTimeOfSessionPracticumOfCompany(final int id) {
		final List<SessionPracticum> sessions = (List<SessionPracticum>) this.findManySessionPracticumsByCompanyId(id);
		if (sessions.isEmpty())
			return null;
		final List<Double> durationsOfSessions = new ArrayList<>();
		for (final SessionPracticum ls : sessions)
			durationsOfSessions.add(this.getDurationInHours(ls));

		return durationsOfSessions.stream().min(Double::compare).orElse(null);

	}
	default Double maximunTimeOfSessionPracticumOfCompany(final int id) {
		final List<SessionPracticum> sessions = (List<SessionPracticum>) this.findManySessionPracticumsByCompanyId(id);
		if (sessions.isEmpty())
			return null;
		final List<Double> durationsOfSessions = new ArrayList<>();
		for (final SessionPracticum ls : sessions)
			durationsOfSessions.add(this.getDurationInHours(ls));

		return durationsOfSessions.stream().max(Double::compare).orElse(null);

	}

	default Double averageTimeOfPracticumOfCompany(final int id) {
		final List<Practicum> practicum = (List<Practicum>) this.findManyPracticumByCompanyId(id);
		if (practicum.isEmpty())
			return null;
		return practicum.stream().mapToDouble(x -> this.getTotalTime(x.getId())).average().getAsDouble();
	}
	default Double deviationTimeOfPracticumOfCompany(final int id) {
		final List<Practicum> practicum = (List<Practicum>) this.findManyPracticumByCompanyId(id);
		if (practicum.size() < 2)
			return null;
		final Double mean = this.averageTimeOfPracticumOfCompany(id);
		final List<Double> durationsOfPracticum = practicum.stream().map(x -> this.getTotalTime(x.getId())).collect(Collectors.toList());
		final Double numerator = durationsOfPracticum.stream().mapToDouble(x -> (x - mean) * (x - mean)).sum();
		return Math.sqrt(numerator / durationsOfPracticum.size());
	}

	default Double minimumTimeOfPracticumOfCompany(final int id) {
		final List<Practicum> practicum = (List<Practicum>) this.findManyPracticumByCompanyId(id);
		if (practicum.isEmpty())
			return null;
		return practicum.stream().mapToDouble(x -> this.getTotalTime(x.getId())).min().getAsDouble();
	}

	default Double maximumTimeOfPracticumOfCompany(final int id) {
		final List<Practicum> practicum = (List<Practicum>) this.findManyPracticumByCompanyId(id);
		if (practicum.isEmpty())
			return null;
		return practicum.stream().mapToDouble(x -> this.getTotalTime(x.getId())).max().getAsDouble();
	}
	default Double getTotalTime(final int id) {
		final Collection<SessionPracticum> sessions = this.findManySessionPracticumsByPracticumId(id);
		double res = 0.;
		for (final SessionPracticum session : sessions)
			res += this.getDurationInHours(session);
		return res;
	}

}
