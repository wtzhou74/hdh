package com.healthcurrent.data_management.service.hdh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcurrent.data_management.model.test.ImmunizationPatient;
import com.healthcurrent.data_management.model.test.Patient;
import com.healthcurrent.data_management.model.test.VitalSignsPatient;
import com.healthcurrent.data_management.service.WriteDataToCSV;

@Service
public class ImmunizationService {

	@Autowired
	AssembleRequest assembleRequest;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RetrievePatients retrievePatients;
	
	@Autowired
	DataSourceService dataSourceService;
	
	@Autowired
	WriteDataToCSV writDataToCSV;
	
	@Value("${hdh.host}")
	String host;
	
	@Value("${hdh.baseUrl}")
	String baseUrl;
	
	@Value("${hdh.apiVersion}")
	String apiVersion;
	
	@Value("${hdh.endpoint.immunizations}")
	String immunizations;
	
	public List<ImmunizationPatient> getImmunizationPatient() {
		HttpEntity<String> request = assembleRequest.getRequest();
		ResponseEntity<String> response = restTemplate.exchange(host + baseUrl + apiVersion + immunizations, HttpMethod.GET, request, String.class);

		List<ImmunizationPatient> immunizationPatients = new ArrayList<ImmunizationPatient>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(response.getBody());
			Iterator<JsonNode> itr = root.iterator();
			while(itr.hasNext()) {
				//JsonNode node1 = itr.next();
				Iterator<JsonNode> childItr = itr.next().iterator();
				int i = 0;
				while (childItr.hasNext()) {
					JsonNode child = childItr.next();
					ImmunizationPatient ip = new ImmunizationPatient();
					String immunizationID = child.path("id").textValue();
					String immunizationLocalId = child.path("localId").textValue();
					String description = child.path("description").textValue();
					String dataSource = child.path("dataSource").textValue();//dataSourceId
					// TODO call dataSouce API to get the identifier
					String dataSourceIdentifier = dataSourceService.findDataSourceNameById(dataSource);
					
					String patientLocalId = child.path("patientLocalId").textValue();
					
					Patient patient = retrievePatients.getPatientsByLocalIdAndSource(patientLocalId, dataSource);
					
					System.out.println("No: " + i++ + " " + immunizationID);
					ip.setImmunizationId(immunizationID);
					ip.setImmunizationLocalId(immunizationLocalId);
					ip.setDescription(description);
					ip.setDataSource(dataSource);
					ip.setDataSourceName(dataSourceIdentifier);
					ip.setPatientLocalId(patientLocalId);
					ip.setPatientId(patient.getPatientId());
					ip.setFirst(patient.getFirst());
					ip.setMiddle(patient.getMiddle());
					ip.setLast(patient.getLast());
					ip.setDob(patient.getDob());
					
					
					immunizationPatients.add(ip);
				}
			}
			// write data to CSV file
			writDataToCSV.writePatientImmunizationDataToCSV(immunizationPatients);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return immunizationPatients;
	}
}
