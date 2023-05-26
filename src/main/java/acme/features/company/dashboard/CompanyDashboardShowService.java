
package acme.features.company.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.CompanyDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	@Autowired
	protected CompanyDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CompanyDashboard object;
		final Double averageTimeOfSessionPracticums;
		final Double deviationTimeOfSessionPracticums;
		final Double minimumTimeOfSessionPracticums;
		final Double maximumTimeOfSessionPracticums;
		final Double averageTimeOfPracticums;
		final Double deviationTimeOfPracticums;
		final Double minimumTimeOfPracticums;
		final Double maximumTimeOfPracticums;
		int companyId;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		averageTimeOfSessionPracticums = this.repository.averageTimeOfSessionPracticumOfCompany(companyId);
		deviationTimeOfSessionPracticums = this.repository.deviationTimeOfSessionPracticumOfCompany(companyId);
		minimumTimeOfSessionPracticums = this.repository.minimumTimeOfSessionPracticumOfCompany(companyId);
		maximumTimeOfSessionPracticums = this.repository.maximunTimeOfSessionPracticumOfCompany(companyId);
		averageTimeOfPracticums = this.repository.averageTimeOfPracticumOfCompany(companyId);
		deviationTimeOfPracticums = this.repository.deviationTimeOfPracticumOfCompany(companyId);
		minimumTimeOfPracticums = this.repository.minimumTimeOfPracticumOfCompany(companyId);
		maximumTimeOfPracticums = this.repository.maximumTimeOfPracticumOfCompany(companyId);
		object = new CompanyDashboard();

		object.setAverageSessionPracticumTime(averageTimeOfSessionPracticums);
		object.setDeviationSessionPracticumTime(deviationTimeOfSessionPracticums);
		object.setMinimumSessionPracticumTime(minimumTimeOfSessionPracticums);
		object.setMaximumSessionPracticumTime(maximumTimeOfSessionPracticums);
		object.setAveragePracticaLength(averageTimeOfPracticums);
		object.setDeviationPracticaLength(deviationTimeOfPracticums);
		object.setMinimumPracticaLength(minimumTimeOfPracticums);
		object.setMaximumPracticaLength(maximumTimeOfPracticums);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CompanyDashboard object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "averageSessionPracticumTime", "deviationSessionPracticumTime", //
			"minimumSessionPracticumTime", "maximumSessionPracticumTime", "averagePracticaLength", //
			"deviationPracticaLength", "minimumPracticaLength", "maximumPracticaLength");

		super.getResponse().setData(tuple);
	}

}
