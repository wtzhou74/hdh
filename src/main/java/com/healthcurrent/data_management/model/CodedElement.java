package com.healthcurrent.data_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coded_element")
public class CodedElement {

	@Id
	private Long codedElementKey;
	private Long conceptKey;
	private String code;
	private String label;
	private String codeSystem;
	private Long facilityKey;
	private Integer codedElemUsageKey;
	
	public Long getCodedElementKey() {
		return codedElementKey;
	}
	public void setCodedElementKey(Long codedElementKey) {
		this.codedElementKey = codedElementKey;
	}
	public Long getConceptKey() {
		return conceptKey;
	}
	public void setConceptKey(Long conceptKey) {
		this.conceptKey = conceptKey;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCodeSystem() {
		return codeSystem;
	}
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}
	public Integer getCodedElemUsageKey() {
		return codedElemUsageKey;
	}
	public void setCodedElemUsageKey(Integer codedElemUsageKey) {
		this.codedElemUsageKey = codedElemUsageKey;
	}
	public Long getFacilityKey() {
		return facilityKey;
	}
	public void setFacilityKey(Long facilityKey) {
		this.facilityKey = facilityKey;
	}
	
	
}
