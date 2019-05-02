package com.healthcurrent.data_management.service.hdh;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcurrent.data_management.model.test.Patient;
import com.healthcurrent.data_management.model.test.ProcedurePatient;
import com.healthcurrent.data_management.model.test.VitalSignsPatient;

@Service
public class RetrievePatients {

	@Autowired
	private AssembleRequest assembleRequest;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${hdh.host}")
	String host;
	
	@Value("${hdh.baseUrl}")
	String baseUrl;
	
	@Value("${hdh.apiVersion}")
	String apiVersion;
	
	@Value("${hdh.endpoint.patients}")
	String patients;
	
	public JsonNode getPatients() {
		HttpEntity<String> request = assembleRequest.getRequest();
		ResponseEntity<String> response = restTemplate.exchange(host + baseUrl + apiVersion + patients, HttpMethod.GET, request, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
//			Map<String, Object> jsonMap 
//			  = mapper.readValue(response.getBody(), new TypeReference<Map<String,Object>>(){});
			root = mapper.readTree(response.getBody());
			Iterator<JsonNode> itr = root.iterator();
			int rootCounter = 0;
			int childCounter = 0;
			while(itr.hasNext()) {
				rootCounter++;
				//JsonNode node1 = itr.next();
				Iterator<JsonNode> childItr = itr.next().iterator();
				while (childItr.hasNext()) {
					childCounter++;
					JsonNode child = childItr.next();
					String patientId = child.path("id").textValue();
					System.out.println("patientId: " + patientId);
				}
				
			}
			
			
			System.out.println("rootCounter" + rootCounter + " childCounter=" + childCounter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}
	
	/**
	 * Retrieve patient data regarding specific procedures
	 * @param patientLocalId
	 * @param dataSource
	 * */
	public void getPatientsRegardingSpecificProcedures(String patientLocalId, String dataSouce, ProcedurePatient procedurePatient) {
		HttpEntity<String> request = assembleRequest.getRequest();
		String fullUrl = host + baseUrl + apiVersion + patients + "?alias[localId]=" + patientLocalId + "&alias[dataSource]=" + dataSouce;
		ResponseEntity<String> response = restTemplate.
				exchange(fullUrl, HttpMethod.GET, request, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(response.getBody());
			Iterator<JsonNode> itr = root.iterator();
			while(itr.hasNext()) {
				Iterator<JsonNode> childItr = itr.next().iterator();
				while (childItr.hasNext()) {
					JsonNode child = childItr.next();
					String patientId = child.path("id").textValue();
					String dob = child.path("dob").path("value").textValue();
					if (dob.length() > 8) {
						dob = dob.substring(0, 8);
					}
					String last = child.path("name").path("last").textValue();
					String first = child.path("name").path("first").textValue();
					String middle = "";
					if (!child.path("name").path("middle").isNull()) {
						middle = child.path("name").path("middle").textValue();
					}
					
					procedurePatient.setPatientId(patientId);
					procedurePatient.setDob(dob);
					procedurePatient.setFirst(first);
					procedurePatient.setLast(last);
					procedurePatient.setMiddle(middle);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getPatientsRegardingSpecificVitalSigns(String patientLocalId, String dataSouce, VitalSignsPatient vitalSignsPatient) {
		HttpEntity<String> request = assembleRequest.getRequest();
		String fullUrl = host + baseUrl + apiVersion + patients + "?alias[localId]=" + patientLocalId + "&alias[dataSource]=" + dataSouce;
		ResponseEntity<String> response = restTemplate.
				exchange(fullUrl, HttpMethod.GET, request, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(response.getBody());
			Iterator<JsonNode> itr = root.iterator();
			while(itr.hasNext()) {
				Iterator<JsonNode> childItr = itr.next().iterator();
				while (childItr.hasNext()) {
					JsonNode child = childItr.next();
					String patientId = child.path("id").textValue();				
					String dob = child.path("dob").path("value").textValue();
					if (dob.length() > 8) {
						dob = dob.substring(0, 8);
					}
					String last = child.path("name").path("last").textValue();
					String first = child.path("name").path("first").textValue();
					String middle = "";
					if (!child.path("name").path("middle").isNull()) {
						middle = child.path("name").path("middle").textValue();
					}
					
					vitalSignsPatient.setPatientId(patientId);
					vitalSignsPatient.setDob(dob);
					vitalSignsPatient.setFirst(first);
					vitalSignsPatient.setLast(last);
					vitalSignsPatient.setMiddle(middle);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Find the patient by patient local Id and data source ID
	 * */
	public Patient getPatientsByLocalIdAndSource (String patientLocalId, String dataSource) {
		HttpEntity<String> request = assembleRequest.getRequest();
		String fullUrl = host + baseUrl + apiVersion + patients + "?alias[localId]=" + patientLocalId + "&alias[dataSource]=" + dataSource;
		ResponseEntity<String> response = restTemplate.
				exchange(fullUrl, HttpMethod.GET, request, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		Patient patient = new Patient();
		try {
			root = mapper.readTree(response.getBody());
			Iterator<JsonNode> itr = root.iterator();
			
			while(itr.hasNext()) {
				Iterator<JsonNode> childItr = itr.next().iterator();
				while (childItr.hasNext()) {
					JsonNode child = childItr.next();
					String patientId = child.path("id").textValue();				
					String dob = child.path("dob").path("value").textValue();
					if (dob.length() > 8) {
						dob = dob.substring(0, 8);
					}
					String last = child.path("name").path("last").textValue();
					String first = child.path("name").path("first").textValue();
					String middle = "";
					if (!child.path("name").path("middle").isNull()) {
						middle = child.path("name").path("middle").textValue();
					}
					
					patient.setPatientId(patientId);
					patient.setDob(dob);
					patient.setLast(last);
					patient.setFirst(first);
					patient.setMiddle(middle);
					// Given local id and data source, only one patient will be found
					break;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patient;
	}
}
