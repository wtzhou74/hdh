package com.healthcurrent.data_management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This abstract class will be extended by other entities.
 * Using Spring Boot's JPA Auditing feature to automatically populate @createdAt and @updatedAt values
 * when a particular entity is inserted/updated in the database.
 * @EntityListeners annotation will automatically populate createdAt and updatedAt values when the entities are persisted.
 * */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
		value = {"createAt", "lastUpdate"},
		allowGetters = true
)
public abstract class AuditModel implements Serializable {

	/*
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name = "created_at", nullable = false, updatable = false)
	 * 
	 * @CreatedDate private Date createdAt;
	 */
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, updatable = false)
	@LastModifiedDate
	private Date lastUpdate;

	/*
	 * public Date getCreatedAt() { return createdAt; }
	 * 
	 * public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
	 */

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
	
	
}
