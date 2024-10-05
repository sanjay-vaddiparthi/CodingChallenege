package org.hexaware.coding.entity;

import java.time.LocalDate;

public class JobApplication {
	
	private int applicationID;
	private JobListing jobID;
	private Applicant applicantID;
	private LocalDate applicationDate;
	private String coverLetter;
	
	public JobApplication() {
		super();
	}

	public JobApplication(int applicationID, JobListing jobID, Applicant applicantID, LocalDate applicationDate,
			String coverLetter) {
		super();
		this.applicationID = applicationID;
		this.jobID = jobID;
		this.applicantID = applicantID;
		this.applicationDate = applicationDate;
		this.coverLetter = coverLetter;
	}

	public int getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}

	public JobListing getJobID() {
		return jobID;
	}

	public void setJobID(JobListing jobID) {
		this.jobID = jobID;
	}

	public Applicant getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(Applicant applicantID) {
		this.applicantID = applicantID;
	}

	public LocalDate getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	@Override
	public String toString() {
		return "JobApplication [applicationID=" + applicationID + ", jobID=" + jobID + ", applicantID=" + applicantID
				+ ", applicationDate=" + applicationDate + ", coverLetter=" + coverLetter + "]";
	}
	
	
	 
	 

}
