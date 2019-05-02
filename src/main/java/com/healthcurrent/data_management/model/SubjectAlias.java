package com.healthcurrent.data_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject_alias")
public class SubjectAlias {

	@Id
	private Long subjectAliasKey;
	private Integer subjectAliasTypeKey;
	private Long subjectKey;
	private String alias;
	private Integer facilityKey;
	public Long getSubjectAliasKey() {
		return subjectAliasKey;
	}
	public void setSubjectAliasKey(Long subjectAliasKey) {
		this.subjectAliasKey = subjectAliasKey;
	}
	public Integer getSubjectAliasTypeKey() {
		return subjectAliasTypeKey;
	}
	public void setSubjectAliasTypeKey(Integer subjectAliasTypeKey) {
		this.subjectAliasTypeKey = subjectAliasTypeKey;
	}
	public Long getSubjectKey() {
		return subjectKey;
	}
	public void setSubjectKey(Long subjectKey) {
		this.subjectKey = subjectKey;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getFacilityKey() {
		return facilityKey;
	}
	public void setFacilityKey(Integer facilityKey) {
		this.facilityKey = facilityKey;
	}
	
	
	
}
