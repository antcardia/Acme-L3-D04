
package acme.features.company.sessionPracticum;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanySessionPracticumRepository repository;


	// AbstractService interface ----------------------------------------------
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
		Practicum practicum;
		SessionPracticum sessionPracticum;
		sessionPracticumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		status = practicum != null && sessionPracticum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		SessionPracticum object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionPracticumById(id);
		super.getBuffer().setData(object);
	}
	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;
		super.bind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation");
	}
	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startTimeError;

			startTimeError = MomentHelper.isAfter(object.getStartTime(), MomentHelper.deltaFromCurrentMoment((long) 1000 * 60 * 60 * 24 * 7 - 1, ChronoUnit.MILLIS));

			super.state(startTimeError, "startTime", "company.practicum-session.form.error.at-least-one-week-ahead");
		}
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startTimeStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;
			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59
			startTimeStatus = MomentHelper.isAfterOrEqual(object.getStartTime(), inferiorLimitDate);
			startTimeStatus &= MomentHelper.isBeforeOrEqual(object.getStartTime(), upperLimitDate);
			super.state(startTimeStatus, "startTime", "company.practicum-session.form.error.date-out-of-bounds");
		}
		if (!super.getBuffer().getErrors().hasErrors("finishTime")) {
			boolean finishTimeError;
			finishTimeError = MomentHelper.isBefore(object.getStartTime(), object.getFinishTime());
			super.state(finishTimeError, "finishTime", "company.practicum-session.form.error.end-before-start");
		}
		if (!super.getBuffer().getErrors().hasErrors("finishTime")) {
			boolean finishTimeErrorDuration;

			finishTimeErrorDuration = !MomentHelper.isLongEnough(object.getStartTime(), object.getFinishTime(), (long) 1000 * 60 * 60 * 24 * 7 + 1, ChronoUnit.MILLIS);

			super.state(finishTimeErrorDuration, "finishTime", "company.practicum-session.form.error.duration");
		}
		if (!super.getBuffer().getErrors().hasErrors("finishTime")) {
			boolean finishTimeStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;
			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59
			finishTimeStatus = MomentHelper.isAfterOrEqual(object.getFinishTime(), inferiorLimitDate);
			finishTimeStatus &= MomentHelper.isBeforeOrEqual(object.getFinishTime(), upperLimitDate);
			super.state(finishTimeStatus, "finishTime", "company.practicum-session.form.error.date-out-of-bounds");
		}
	}
	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.save(object);
	}
	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("confirmation", "false");

		super.getResponse().setData(tuple);
	}
}
