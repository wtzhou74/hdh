package com.healthcurrent.data_management.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "clinical_item_clinical_message")
public class ClinicalItemClinicalMessage {

	@EmbeddedId
	private ClinicalItemClinicalMessageId entryId;

	public ClinicalItemClinicalMessageId getEntryId() {
		return entryId;
	}

	public void setEntryId(ClinicalItemClinicalMessageId entryId) {
		this.entryId = entryId;
	}
	
	
}
