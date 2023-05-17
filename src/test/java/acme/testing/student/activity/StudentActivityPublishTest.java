/*
 * EmployerJobPublishTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import acme.testing.TestHarness;

public class StudentActivityPublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String reference) {
		// HINT: this test authenticates as an employer, lists his or her jobs,
		// HINT: then selects one of them, and publishes it.

		super.signIn("employer1", "employer1");

		super.clickOnMenu("Employer", "List my jobs");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, reference);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String reference) {
		// HINT: this test attempts to publish a job that cannot be published, yet.

		super.signIn("employer1", "employer1");

		super.clickOnMenu("Employer", "List my jobs");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, reference);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkAlertExists(false);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a job with a role other than "Employer".

		final Collection<Job> jobs;
		final String params;

		//jobs = this.repository.findManyJobsByEmployerUsername("employer1");
		//for (final Job job : jobs)
		//	if (job.isDraftMode()) {
		//	params = String.format("id=%d", job.getId());

		//	super.checkLinkExists("Sign in");
		//	super.request("/employer/job/publish", params);
		//	super.checkPanicExists();

		//	super.signIn("administrator", "administrator");
		//	super.request("/employer/job/publish", params);
		//	super.checkPanicExists();
		//	super.signOut();

		//	super.signIn("worker1", "worker1");
		//	super.request("/employer/job/publish", params);
		//	super.checkPanicExists();
		//	super.signOut();
	}
	//}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a published job that was registered by the principal.

		final Collection<Job> jobs;
		final String params;

		super.signIn("employer1", "employer1");
		//jobs = this.repository.findManyJobsByEmployerUsername("employer1");
		//for (final Job job : jobs)
		//if (!job.isDraftMode()) {
		//params = String.format("id=%d", job.getId());
		//super.request("/employer/job/publish", params);
		//}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to publish a job that wasn't registered by the principal,
		// HINT+ be it published or unpublished.

		final Collection<Job> jobs;
		final String params;

		super.signIn("employer2", "employer2");
		//jobs = this.repository.findManyJobsByEmployerUsername("employer1");
		//for (final Job job : jobs) {
		//	params = String.format("id=%d", job.getId());
		//	super.request("/employer/job/publish", params);
		//}
		super.signOut();
	}

}
