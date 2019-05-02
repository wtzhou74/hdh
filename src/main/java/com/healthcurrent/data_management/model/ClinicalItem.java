package com.healthcurrent.data_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "clinical_item")
public class ClinicalItem {

	@Id
	@NotBlank
	private Long clinicalItemKey;
	@NotBlank
	private String id;
	private String label;
	private Long codeKey;
	private Long facilityKey;
	private Long subjectKey;
	private Long sourceFacilityKey;
	private Long clinicalItemTypeKey;
	private Long subjectAliasKey;
	public Long getClinicalItemKey() {
		return clinicalItemKey;
	}
	public void setClinicalItemKey(Long clinicalItemKey) {
		this.clinicalItemKey = clinicalItemKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getCodeKey() {
		return codeKey;
	}
	public void setCodeKey(Long codeKey) {
		this.codeKey = codeKey;
	}
	public Long getFacilityKey() {
		return facilityKey;
	}
	public void setFacilityKey(Long facilityKey) {
		this.facilityKey = facilityKey;
	}
	public Long getSubjectKey() {
		return subjectKey;
	}
	public void setSubjectKey(Long subjectKey) {
		this.subjectKey = subjectKey;
	}
	public Long getSourceFacilityKey() {
		return sourceFacilityKey;
	}
	public void setSourceFacilityKey(Long sourceFacilityKey) {
		this.sourceFacilityKey = sourceFacilityKey;
	}
	public Long getClinicalItemTypeKey() {
		return clinicalItemTypeKey;
	}
	public void setClinicalItemTypeKey(Long clinicalItemTypeKey) {
		this.clinicalItemTypeKey = clinicalItemTypeKey;
	}
	public Long getSubjectAliasKey() {
		return subjectAliasKey;
	}
	public void setSubjectAliasKey(Long subjectAliasKey) {
		this.subjectAliasKey = subjectAliasKey;
	}
	
	
}
