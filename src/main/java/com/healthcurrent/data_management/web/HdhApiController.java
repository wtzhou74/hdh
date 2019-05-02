package com.healthcurrent.data_management.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.healthcurrent.data_management.model.test.ImmunizationPatient;
import com.healthcurrent.data_management.model.test.ProcedurePatient;
import com.healthcurrent.data_management.model.test.VitalSignsPatient;
import com.healthcurrent.data_management.service.hdh.ImmunizationService;
import com.healthcurrent.data_management.service.hdh.RetrievePatients;
import com.healthcurrent.data_management.service.hdh.RetrieveProcedures;
import com.healthcurrent.data_management.service.hdh.VitalSignService;

@RestController
@RequestMapping("/hdh")
public class HdhApiController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RetrievePatients retrievePatients;
	
	@Autowired
	RetrieveProcedures retrieveProcedures;
	
	@Autowired
	VitalSignService vitalSignService;
	
	@Autowired
	ImmunizationService immunizationService;
	
	@RequestMapping(value = "/getPatients", method = RequestMethod.GET)
	public JsonNode getPatients() {
		// return JSONObject.quote("Hello World"); //https://mvnrepository.com/artifact/org.json/json
		return retrievePatients.getPatients();
	}
	
	@RequestMapping(value = "/getPatients/procedures", method = RequestMethod.GET)
	public List<ProcedurePatient> getProcedures() {
		return retrieveProcedures.getProcedures();
	}
	
	@RequestMapping(value = "/getPatients/vital-signs", method = RequestMethod.GET)
	public List<VitalSignsPatient> getVitalSigns() {
		return vitalSignService.getVitalSignPatient();
	}
	
	@RequestMapping(value = "/getPatients/immunizations", method = RequestMethod.GET)
	public List<ImmunizationPatient> getPatientImmunization() {
		return immunizationService.getImmunizationPatient();
	}
}
