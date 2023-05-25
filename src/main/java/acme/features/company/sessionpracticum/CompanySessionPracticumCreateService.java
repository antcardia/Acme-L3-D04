
package acme.features.company.sessionpracticum;

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
public class CompanySessionPracticumCreateService extends AbstractService<Company, SessionPracticum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);

		object = new SessionPracticum();
		object.setTitle("");
		object.setAbstract$("");
		object.setStartTime(MomentHelper.getCurrentMoment());
		object.setFinishTime(MomentHelper.getCurrentMoment());
		object.setFurtherInformation("");
		object.setAdditional(false);

		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional");
	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startTime")) {
			boolean startTimeError;

			startTimeError = MomentHelper.isAfter(object.getStartTime(), MomentHelper.deltaFromCurrentMoment((long) 1000 * 60 * 60 * 24 * 7 - 1, ChronoUnit.MILLIS));

			super.state(startTimeError, "startTime", "company.practicum-session.form.error.at-least-one-week-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("startTime")) {
			boolean startTimeStatus;
			Date inferiorLimitTime;
			Date upperLimitTime;

			inferiorLimitTime = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitTime = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			startTimeStatus = MomentHelper.isAfterOrEqual(object.getStartTime(), inferiorLimitTime);
			startTimeStatus &= MomentHelper.isBeforeOrEqual(object.getStartTime(), upperLimitTime);

			super.state(startTimeStatus, "startTime", "company.practicum-session.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishTime")) {
			boolean finishTimeError;

			finishTimeError = MomentHelper.isBefore(object.getStartTime(), object.getFinishTime());

			super.state(finishTimeError, "finishTime", "company.practicum-session.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("endTime")) {
			boolean finishTimeErrorDuration;

			finishTimeErrorDuration = !MomentHelper.isLongEnough(object.getStartTime(), object.getFinishTime(), (long) 1000 * 60 * 60 * 24 * 7 + 1, ChronoUnit.MILLIS);

			super.state(finishTimeErrorDuration, "finishTime", "company.practicum-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishTime")) {
			boolean finishTimeStatus;
			Date inferiorLimitTime;
			Date upperLimitTime;

			inferiorLimitTime = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitTime = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			finishTimeStatus = MomentHelper.isAfterOrEqual(object.getFinishTime(), inferiorLimitTime);
			finishTimeStatus &= MomentHelper.isBeforeOrEqual(object.getFinishTime(), upperLimitTime);

			super.state(finishTimeStatus, "finishTime", "company.practicum-session.form.error.date-out-of-bounds");
		}
		if (!super.getBuffer().getErrors().hasErrors("furtherInformation"))
			super.state(object.getFurtherInformation().length() < 255, "furtherInformation", "company.session-practicum.form.error.outOfRangeLink");
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		int masterId;
		Tuple tuple;

		masterId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "title", "abstract$", "startTime", "finishTime", "furtherInformation", "additional");
		tuple.put("masterId", masterId);
		tuple.put("confirmation", "false");

		super.getResponse().setData(tuple);
	}

}
