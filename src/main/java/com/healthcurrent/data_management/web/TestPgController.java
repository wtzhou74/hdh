package com.healthcurrent.data_management.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.healthcurrent.data_management.repository.ActorRepository;
import com.healthcurrent.data_management.repository.CptRepository;
import com.healthcurrent.data_management.model.Actor;
import com.healthcurrent.data_management.model.CPT;

@RestController
public class TestPgController {

	@Autowired
	private ActorRepository actorRepo;
	
	@Autowired
	private CptRepository cptRepo;
	
	
	@RequestMapping(value="/test/{actorId}/actors", method = RequestMethod.GET) 
	public List<Actor> getActorById(@PathVariable Integer actorId) { 
		  return actorRepo.findActorById(actorId); 
	}
	
	/*
	 * @GetMapping("/test/{firstName}/actor") public List<Actor>
	 * getActorByFirstName(@PathVariable String firstName) { return
	 * actorRepo.findActorByFirstName(firstName); }
	 */
	
	@GetMapping("/test/{firstName}/actor") 
	public List<Actor> getActorByFirstNameOrderByLastName(@PathVariable String firstName) { 
		  return actorRepo.findByFirstNameOrderByLastNameAsc(firstName); 
	}
	 
	
	@GetMapping("/test/actors")
	public List<Actor> getActorById() {
		return actorRepo.findAll();
	}
	
	@RequestMapping(value="/test/top_actors", method = RequestMethod.GET)
	public List<Actor> getTop10ActorByOrderByIdDesc() {
		return actorRepo.findTop10ByOrderByIdDesc();
	}
	
	@RequestMapping(value="/test/dateRange/actors", method = RequestMethod.GET)
	public List<Actor> findByDateRange() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		String startDateStr = "2019-01-01 00:00:00";
		String endDateStr = "2019-03-01 00:00:00";
		
		Date startDate = null, endDate = null;
		try {
			startDate = DateUtils.parseDate(startDateStr, new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM-yyyy" });
			endDate = DateUtils.parseDate(endDateStr, new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM-yyyy" });
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			startDate = new Date();
			endDate = new Date();
			e.printStackTrace();
		}
		return actorRepo.findByLastUpdateBetween(startDate, endDate);
	}
	
	@GetMapping("/test/{conceptKey}/cpt")
	public List<CPT> getCptByConceptKey(@PathVariable Long conceptKey) {
		return cptRepo.getCptByConceptKey(conceptKey);
	}
}
