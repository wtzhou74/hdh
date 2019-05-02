package com.healthcurrent.data_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coded_element_usage")
public class CodedElementUsage {

	@Id
	private Integer codedElemUsageKey;
	private String dbTableName;
	private String dbColumnName;
	private String entityPropertyName;
	private int isRequired;
	private String label;
	private int usageCategory;
	
	public Integer getCodedElemUsageKey() {
		return codedElemUsageKey;
	}
	public void setCodedElemUsageKey(Integer codedElemUsageKey) {
		this.codedElemUsageKey = codedElemUsageKey;
	}
	public String getDbTableName() {
		return dbTableName;
	}
	public void setDbTableName(String dbTableName) {
		this.dbTableName = dbTableName;
	}
	public String getDbColumnName() {
		return dbColumnName;
	}
	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName = dbColumnName;
	}
	public String getEntityPropertyName() {
		return entityPropertyName;
	}
	public void setEntityPropertyName(String entityPropertyName) {
		this.entityPropertyName = entityPropertyName;
	}
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getUsageCategory() {
		return usageCategory;
	}
	public void setUsageCategory(int usageCategory) {
		this.usageCategory = usageCategory;
	}
	
	
}
