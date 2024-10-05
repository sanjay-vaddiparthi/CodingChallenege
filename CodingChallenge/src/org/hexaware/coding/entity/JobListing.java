package org.hexaware.coding.entity;

import java.time.LocalDate;

public class JobListing {
	private int jobID;
    private Company companyID;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private double salary;
    private String jobType;
    private LocalDate postedDate;
    
    public JobListing() {
		super();
	}
    
    
	public JobListing(int jobID, Company companyID, String jobTitle, String jobDescription, String jobLocation, double salary, String jobType, LocalDate postedDate) {
		super();
		this.jobID = jobID;
		this.companyID = companyID;
		this.jobTitle = jobTitle;
		this.jobDescription = jobDescription;
		this.jobLocation = jobLocation;
		this.salary = salary;
		this.jobType = jobType;
		this.postedDate = postedDate;
	}
	
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	public Company getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Company companyID) {
		this.companyID = companyID;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}


	@Override
	public String toString() {
		return "JobListing [jobID=" + jobID + ", companyID=" + companyID + ", jobTitle=" + jobTitle
				+ ", jobDescription=" + jobDescription + ", jobLocation=" + jobLocation + ", salary=" + salary
				+ ", jobType=" + jobType + ", postedDate=" + postedDate + "]";
	}


	public void setCompanyID(int int1) {
		// TODO Auto-generated method stub
		
	}
	
}

