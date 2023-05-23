
package acme.features.company.sessionpracticum;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumConfirmService extends AbstractService<Company, SessionPracticum> {

	// Constants -------------------------------------------------------------
	protected static final String[]				PROPERTIES_BIND		= {
		"title", "abstract$", "startTime", "finishTime", "furtherInformation"
	};

	protected static final String[]				PROPERTIES_UNBIND	= {
		"title", "abstract", "startTime", "finishTime", "furtherInformation", "additional", "draftMode"
	};
	public static final int						ONE_WEEK			= 1;

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanySessionPracticumRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionPracticumId;
		SessionPracticum sessionPracticum;
		Practicum practicum;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);
		status = false;

		if (practicum != null && sessionPracticum != null) {
			Principal principal;
			boolean hasExtraAvailable;
			boolean isPublishedAndHasExtraAvailable;

			principal = super.getRequest().getPrincipal();
			hasExtraAvailable = this.repository.findManySessionPracticumsByExtraAvailableAndPracticumId(practicum.getId()).isEmpty();
			isPublishedAndHasExtraAvailable = !sessionPracticum.isDraftMode() && !practicum.isDraftMode() && hasExtraAvailable;

			status = isPublishedAndHasExtraAvailable && principal.hasRole(practicum.getCompany());
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum sessionPracticum;
		int sessionPracticumId;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		sessionPracticum.setDraftMode(true);

		super.getBuffer().setData(sessionPracticum);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, CompanySessionPracticumConfirmService.PROPERTIES_BIND);
	}

	@Override
	public void validate(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		if (!super.getBuffer().getErrors().hasErrors("start") || !super.getBuffer().getErrors().hasErrors("end")) {
			Date start;
			Date end;
			Date inAWeekFromNow;
			Date inAWeekFromStart;

			start = sessionPracticum.getStartTime();
			end = sessionPracticum.getFinishTime();
			inAWeekFromNow = MomentHelper.deltaFromCurrentMoment(CompanySessionPracticumConfirmService.ONE_WEEK, ChronoUnit.WEEKS);
			inAWeekFromStart = MomentHelper.deltaFromMoment(start, CompanySessionPracticumConfirmService.ONE_WEEK, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("startTime"))
				super.state(MomentHelper.isAfter(start, inAWeekFromNow), "startTime", "company.sessionpracticum.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("finishTime"))
				super.state(MomentHelper.isAfter(end, inAWeekFromStart), "finishTime", "company.sessionpracticum.error.end-after-start");
		}
		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(sessionPracticum.getFurtherInformation().length() < 255, "furtherInformation", "company.sessionpracticum.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		sessionPracticum.setDraftMode(true);
		this.repository.save(sessionPracticum);
	}

	@Override
	public void unbind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		Practicum practicum;
		Tuple tuple;

		practicum = sessionPracticum.getPracticum();
		tuple = super.unbind(sessionPracticum, CompanySessionPracticumConfirmService.PROPERTIES_UNBIND);
		tuple.put("masterId", practicum.getId());
		tuple.put("draftMode", practicum.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
