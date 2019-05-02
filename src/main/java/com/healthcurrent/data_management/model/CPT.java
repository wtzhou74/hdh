package com.healthcurrent.data_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cpt")
public class CPT {

	@Id
	private Long conceptKey;
	
	private String label;

	public Long getConceptKey() {
		return conceptKey;
	}

	public void setConceptKey(Long conceptKey) {
		this.conceptKey = conceptKey;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
