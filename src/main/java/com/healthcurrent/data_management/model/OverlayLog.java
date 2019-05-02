package com.healthcurrent.data_management.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "overlay_log")
public class OverlayLog {

	@Id
	@NotBlank
	private Long overlayLogKey;
	@NotBlank
	private String overlayStatus;
	private int autoCorrected;
	private Date receivedDate;
	private Date msgDate;
	private String sendingFacility;
	private String controlId;
	private String facilityId;
	private String patientId;
	
	
	public Long getOverlayLogKey() {
		return overlayLogKey;
	}
	public void setOverlayLogKey(Long overlayLogKey) {
		this.overlayLogKey = overlayLogKey;
	}
	public String getOverlayStatus() {
		return overlayStatus;
	}
	public void setOverlayStatus(String overlayStatus) {
		this.overlayStatus = overlayStatus;
	}
	public int getAutoCorrected() {
		return autoCorrected;
	}
	public void setAutoCorrected(int autoCorrected) {
		this.autoCorrected = autoCorrected;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public Date getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	public String getSendingFacility() {
		return sendingFacility;
	}
	public void setSendingFacility(String sendingFacility) {
		this.sendingFacility = sendingFacility;
	}
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	
	
}
