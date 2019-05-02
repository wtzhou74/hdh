package com.healthcurrent.data_management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "actor")
//public class Actor extends AuditModel {
public class Actor {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "actor_id", updatable = false, nullable = false)
	private Integer id;
	
	@NotBlank
	private String firstName;
	
	
	@NotBlank 
	private String lastName;
	 
	
	@NotBlank 
	private Date lastUpdate;
	 

	/*
	 * public Integer getActorId() { return actorId; }
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	/*
	 * public void setActorId(Integer actorId) { this.actorId = actorId; }
	 */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
