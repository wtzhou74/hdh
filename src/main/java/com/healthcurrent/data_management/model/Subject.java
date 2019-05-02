package com.healthcurrent.data_management.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject")
public class Subject {

	@Id
	private Long subjectKey;
	private String ssn;
	private Date dob;
	private Long facilityKey;
	public Long getSubjectKey() {
		return subjectKey;
	}
	public void setSubjectKey(Long subjectKey) {
		this.subjectKey = subjectKey;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Long getFacilityKey() {
		return facilityKey;
	}
	public void setFacilityKey(Long facilityKey) {
		this.facilityKey = facilityKey;
	}
	
	
}
