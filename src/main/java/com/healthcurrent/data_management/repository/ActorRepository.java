package com.healthcurrent.data_management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	/*
	 * Reference: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
	 * 	- Table 3: Supported keywords inside method names
	 * */
	List<Actor> findActorById(Integer actorId);
	List<Actor> findActorByFirstName(String firstName);
	List<Actor> findByFirstNameOrderByLastNameAsc(String firstName);
	List<Actor> findTop10ByOrderByIdDesc();
	List<Actor> findByLastUpdateBetween(Date startDate, Date endDate);
}
