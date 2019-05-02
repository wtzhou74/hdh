package com.healthcurrent.data_management.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "clinical_message")
public class ClinicalMessage {

	@Id
	@NotBlank
	private Long clinicalMessageKey;
	private Date messageDate;
	private String messageControlId;
	private Integer clinicalMessageTypeKey;
	private Long subjectKey;
	private Integer sourceKey;
	private Date receivedDate;
	private String subjectAliasKey;
	
	
	public Long getClinicalMessageKey() {
		return clinicalMessageKey;
	}
	public void setClinicalMessageKey(Long clinicalMessageKey) {
		this.clinicalMessageKey = clinicalMessageKey;
	}
	public Date getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
	public String getMessageControlId() {
		return messageControlId;
	}
	public void setMessageControlId(String messageControlId) {
		this.messageControlId = messageControlId;
	}
	
	public Integer getClinicalMessageTypeKey() {
		return clinicalMessageTypeKey;
	}
	public void setClinicalMessageTypeKey(Integer clinicalMessageTypeKey) {
		this.clinicalMessageTypeKey = clinicalMessageTypeKey;
	}
	public Long getSubjectKey() {
		return subjectKey;
	}
	public void setSubjectKey(Long subjectKey) {
		this.subjectKey = subjectKey;
	}
	public Integer getSourceKey() {
		return sourceKey;
	}
	public void setSourceKey(Integer sourceKey) {
		this.sourceKey = sourceKey;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getSubjectAliasKey() {
		return subjectAliasKey;
	}
	public void setSubjectAliasKey(String subjectAliasKey) {
		this.subjectAliasKey = subjectAliasKey;
	}
	
	
}
