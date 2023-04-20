
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
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

	// Constants --------------------------------------------------------------
	protected static final String[]				PROPERTIES_BIND		= {
		"title", "abstract$", "startTime", "finishTime", "furtherInformation"
	};

	protected static final String[]				PROPERTIES_UNBIND	= {
		"title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional", "draftMode"
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
		Principal principal;

		principal = super.getRequest().getPrincipal();
		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);

		status = practicum != null && (practicum.isDraftMode() || sessionPracticum.isAdditional()) && principal.hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum sessionPracticum;
		int sessionPracticumId;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);

		super.getBuffer().setData(sessionPracticum);
	}

	@Override
	public void bind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		super.bind(sessionPracticum, CompanySessionPracticumUpdateService.PROPERTIES_BIND);
	}

	@Override
	public void validate(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		if (!super.getBuffer().getErrors().hasErrors("startTime") || !super.getBuffer().getErrors().hasErrors("finishTime")) {
			Date startTime;
			Date finishTime;
			Date inAWeekFromNow;
			Date inAWeekFromStart;

			startTime = sessionPracticum.getStartTime();
			finishTime = sessionPracticum.getFinishTime();
			inAWeekFromNow = MomentHelper.deltaFromCurrentMoment(CompanySessionPracticumUpdateService.ONE_WEEK, ChronoUnit.WEEKS);
			inAWeekFromStart = MomentHelper.deltaFromMoment(startTime, CompanySessionPracticumUpdateService.ONE_WEEK, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("startTime"))
				super.state(MomentHelper.isAfter(startTime, inAWeekFromNow), "startTime", "company.session-practicum.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("finishTime"))
				super.state(MomentHelper.isAfter(finishTime, inAWeekFromStart), "finishTime", "company.session-practicum.error.end-after-start");
		}
	}

	@Override
	public void perform(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		this.repository.save(sessionPracticum);
	}

	@Override
	public void unbind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		Practicum practicum;
		Tuple tuple;

		practicum = sessionPracticum.getPracticum();
		tuple = super.unbind(sessionPracticum, CompanySessionPracticumUpdateService.PROPERTIES_UNBIND);
		tuple.put("masterId", practicum.getId());
		tuple.put("draftMode", practicum.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
