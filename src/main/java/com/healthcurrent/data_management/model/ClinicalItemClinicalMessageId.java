package com.healthcurrent.data_management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClinicalItemClinicalMessageId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "clinical_item_key")
	private Long clinicalItemKey;
	@Column(name = "clinical_message_key")
	private Long clinicalMessageKey;
	
	
	public ClinicalItemClinicalMessageId() {
	}
	
	public Long getClinicalItemKey() {
		return clinicalItemKey;
	}
	public void setClinicalItemKey(Long clinicalItemKey) {
		this.clinicalItemKey = clinicalItemKey;
	}
	public Long getClinicalMessageKey() {
		return clinicalMessageKey;
	}
	public void setClinicalMessageKey(Long clinicalMessageKey) {
		this.clinicalMessageKey = clinicalMessageKey;
	}
	
	
}
