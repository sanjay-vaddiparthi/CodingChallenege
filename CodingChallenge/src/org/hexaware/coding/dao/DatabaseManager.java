package org.hexaware.coding.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hexaware.coding.entity.*;
import org.hexaware.coding.util.*;
import org.hexaware.coding.exception.*;

public class DatabaseManager {
    Connection con = DBConnection.getConnection();
    ResultSet rs;
    PreparedStatement ps;
    Statement stmt;

    public DatabaseManager() {
        initializeDatabase(); 
    }

    public void initializeDatabase() {
        try {
            Statement stmt = con.createStatement();
            
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS codingchallenge");
            stmt.executeUpdate("USE codingchallenge"); 

            
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY (" +
                    "CompanyID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "CompanyName VARCHAR(255) NOT NULL, " +
                    "Location VARCHAR(255) NOT NULL);");

            
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS JOBLISTING (" +
                    "JobID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "CompanyID INT NOT NULL, " +
                    "JobTitle VARCHAR(255) NOT NULL, " +
                    "JobDescription TEXT NOT NULL, " +
                    "JobLocation VARCHAR(255) NOT NULL, " +
                    "Salary DECIMAL(10, 2) NOT NULL, " +
                    "JobType VARCHAR(50) NOT NULL, " +
                    "PostedDate DATETIME NOT NULL, " +
                    "FOREIGN KEY (CompanyID) REFERENCES Company(CompanyID));");

            
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS APPLICANT (" +
                    "ApplicantID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "FirstName VARCHAR(255) NOT NULL, " +
                    "LastName VARCHAR(255) NOT NULL, " +
                    "Email VARCHAR(255) NOT NULL UNIQUE, " +
                    "Phone VARCHAR(20), " +
                    "Resume TEXT);");

            
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS JOBAPPLICATION (" +
                    "ApplicationID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "JobID INT NOT NULL, " +
                    "ApplicantID INT NOT NULL, " +
                    "ApplicationDate DATETIME NOT NULL, " +
                    "CoverLetter TEXT, " +
                    "FOREIGN KEY (JobID) REFERENCES JOBLISTING(JobID), " +
                    "FOREIGN KEY (ApplicantID) REFERENCES APPLICANT(ApplicantID));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public void insertJobListing(JobListing job) throws NegativeSalaryException{
        try {
        	if(job.getSalary()<0)
        	{
        		throw new NegativeSalaryException("Salary cant be negative");
        	}
            ps = con.prepareStatement("insert into joblisting values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, job.getJobID());
            ps.setInt(2, job.getCompanyID().getCompanyID());
            ps.setString(3,job.getJobTitle());
            ps.setString(4, job.getJobDescription());
            ps.setString(5, job.getJobLocation());
            ps.setDouble(6,job.getSalary());
            String  j =job.getJobType();
            String s = j.toString();
            ps.setObject(7, s);
            ps.setObject(8, job.getPostedDate());
            int rows = ps.executeUpdate();
            if(rows>0) System.out.println(rows+" inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertCompany(Company company) {
        try {
            ps = con.prepareStatement("INSERT INTO COMPANY VALUES (?, ?, ?);");
            ps.setInt(1, company.getCompanyID());
            ps.setString(2, company.getCompanyName());
            ps.setString(3, company.getLocation());
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println(rows + " inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertApplicant(Applicant applicant) {
        try {
            ps = con.prepareStatement("INSERT INTO APPLICANT VALUES (?, ?, ?, ?, ?, ?);");
            ps.setInt(1, applicant.getApplicantID());
            ps.setString(2, applicant.getFirstName());
            ps.setString(3, applicant.getLastName());
            ps.setString(4, applicant.getEmail());
            ps.setString(5, applicant.getPhone());
            ps.setString(6, applicant.getResume());
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println(rows + " inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertJobApplication(JobApplication application) {
        try {
            ps = con.prepareStatement("INSERT INTO JOBAPPLICATION VALUES (?, ?, ?, ?, ?);");
            ps.setInt(1, application.getApplicationID());
            ps.setInt(2, application.getJobID().getJobID());
            ps.setInt(3, application.getApplicantID().getApplicantID());
            ps.setDate(4, Date.valueOf(application.getApplicationDate()));
            ps.setString(5, application.getCoverLetter());
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println(rows + " inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JobListing> getJobListings() {
        List<JobListing> jobListings = new ArrayList<>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM JOBLISTING;");
            while (rs.next()) {
                JobListing job = new JobListing();
                Company comp = new Company();
                job.setJobID(rs.getInt("jobid"));
                comp.setCompanyID(rs.getInt("companyid"));
                job.setCompanyID(comp);
                job.setJobTitle(rs.getString("jobtitle"));
                job.setJobDescription(rs.getString("jobdescription"));
                job.setJobLocation(rs.getString("joblocation"));
                job.setSalary(rs.getDouble("salary"));
                job.setJobType(rs.getString("jobtype"));
                job.setPostedDate(rs.getDate("posteddate").toLocalDate()); // Adjust to convert to LocalDate

                jobListings.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobListings;
    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM COMPANY;");
            while (rs.next()) {
                Company comp = new Company();
                comp.setCompanyID(rs.getInt("companyid"));
                comp.setCompanyName(rs.getString("companyname"));
                comp.setLocation(rs.getString("location"));
                companies.add(comp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public List<Applicant> getApplicants() {
        List<Applicant> applicants = new ArrayList<>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM APPLICANT;");
            while (rs.next()) {
                Applicant app = new Applicant();
                app.setApplicantID(rs.getInt("applicantid"));
                app.setFirstName(rs.getString("firstname"));
                app.setLastName(rs.getString("lastname"));
                app.setEmail(rs.getString("email"));
                app.setPhone(rs.getString("phone"));
                app.setResume(rs.getString("resume"));
                applicants.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applicants;
    }

    public List<JobApplication> getApplicationsForJob(int jobId) {
        List<JobApplication> jobApplications = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM JOBAPPLICATION WHERE JOBID = ?;");
            ps.setInt(1, jobId);
            rs = ps.executeQuery();
            while (rs.next()) {
                JobApplication ja = new JobApplication();
                JobListing jl = new JobListing();
                Applicant app = new Applicant();
                ja.setApplicationID(rs.getInt("applicationid"));
                jl.setJobID(jobId);
                ja.setJobID(jl);
                app.setApplicantID(rs.getInt("applicantid"));
                ja.setApplicantID(app);
                ja.setApplicationDate(rs.getDate("applicationdate").toLocalDate()); // Adjust to convert to LocalDate
                ja.setCoverLetter(rs.getString("coverletter"));
                jobApplications.add(ja);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobApplications;
    }
    
    public void apply(int applicantID,String coverLetter)
    {
    	try {
			ps = con.prepareStatement("insert into jobapplication(jobid,applicantid,applicationdate,coverletter) "
					+ "values(?,?,?,?);");
			System.out.println("Enter job ID from the list given below.");
			List<JobListing> jobs = getJobListings();
			Iterator<JobListing> it = jobs.iterator();
			while(it.hasNext()) {
				System.out.println(it.next());
			}
			Scanner sc = new Scanner(System.in);
			int jid = sc.nextInt();
			ps.setInt(1, jid);
			ps.setInt(2, applicantID);
			LocalDate date = LocalDate.now();
			ps.setObject(3, date);
			ps.setString(4, coverLetter);
			sc.close();
			int rows = ps.executeUpdate();
			if(rows>0) System.out.println(rows+" updated successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void postJob(String jobTitle, String jobDescription,String jobLocation, double salary, String jobType)
   	{
   		try {
   			ps = con.prepareStatement("insert into joblisting(COMPANYID,JOBTITLE,JOBDESCRIPTION,JOBLOCATION,salary,JOBTYPE,POSTEDDATE)"
   					+ "values(?,?,?,?,?,?,?);");
   			System.out.println("Choose your company from the below list:");
   			List<Company> comp = getCompanies();
   			System.out.println(comp);
   			Scanner sc = new Scanner(System.in);
   			int cid = sc.nextInt();
   			ps.setInt(1, cid);	
   			ps.setString(2, jobTitle);
   			ps.setString(3, jobDescription);
   			ps.setString(4, jobLocation);
   			ps.setDouble(5, salary);
   			jobType = jobType.toUpperCase();
   			//JobType jType = JobType.valueOf(jobType);
   			ps.setObject(6, jobType);
   			LocalDate date = LocalDate.now();
   			ps.setObject(7, date);
   			sc.close();
   			int rows = ps.executeUpdate();
   			if(rows>0) System.out.println(rows+" updated successfully.");
   		} catch (SQLException e) {
   			e.printStackTrace();
   		}
   		
   	}

    public void validateEmail(String email) throws InvalidEmailFormatException {
        if (email == null || !email.contains("@")) {
            throw new InvalidEmailFormatException("Email address must contain '@'.");
        }
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new InvalidEmailFormatException("Invalid email format.");
        }
        System.out.println("Email address validation successful.");
    }
    
    public List<JobListing> getJobsBySalaryRange(double minSalary, double maxSalary) throws SQLException {
        List<JobListing> jobListings = new ArrayList<>();
        
        String query = "SELECT jl.JobTitle, c.CompanyName, jl.Salary " +
                       "FROM JobListing jl " +
                       "JOIN Company c ON jl.CompanyID = c.CompanyID " +
                       "WHERE jl.Salary BETWEEN ? AND ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, minSalary);
            ps.setDouble(2, maxSalary);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JobListing job = new JobListing();
                    job.setJobTitle(rs.getString("JobTitle"));
//                    job.setCompanyID(rs.getString("CompanyName")); // Set the company name
                    job.setSalary(rs.getDouble("Salary"));
                    
                    jobListings.add(job);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing salary range query: " + e.getMessage());
            throw e;
        }
        
        return jobListings;
    }


}
