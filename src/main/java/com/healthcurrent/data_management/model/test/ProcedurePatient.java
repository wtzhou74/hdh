package com.healthcurrent.data_management.model.test;

public class ProcedurePatient {

	String procedureId;
	String procedureLocalId;
	String description;
	String dataSource;
	String dataSourceName;
	String patientId;
	String patientLocalId;
	String last;
	String middle;
	String first;
	String dob;
	
	public String getProcedureLocalId() {
		return procedureLocalId;
	}
	public void setProcedureLocalId(String procedureLocalId) {
		this.procedureLocalId = procedureLocalId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProcedureId() {
		return procedureId;
	}
	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientLocalId() {
		return patientLocalId;
	}
	public void setPatientLocalId(String patientLocalId) {
		this.patientLocalId = patientLocalId;
	}
	
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	
}
