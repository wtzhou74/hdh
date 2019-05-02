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
import com.healthcurrent.data_management.model.test.ProcedurePatient;
import com.healthcurrent.data_management.model.test.VitalSignsPatient;
import com.healthcurrent.data_management.service.WriteDataToCSV;

@Service
public class VitalSignService {

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
	
	@Value("${hdh.endpoint.vitalSigns}")
	String vitalSigns;
	
	public List<VitalSignsPatient> getVitalSignPatient() {
		HttpEntity<String> request = assembleRequest.getRequest();
		ResponseEntity<String> response = restTemplate.exchange(host + baseUrl + apiVersion + vitalSigns, HttpMethod.GET, request, String.class);

		List<VitalSignsPatient> vitalSignsPatients = new ArrayList<VitalSignsPatient>();
		
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
					VitalSignsPatient vp = new VitalSignsPatient();
					String vitalSignId = child.path("id").textValue();
					String vitalSignLocalId = child.path("id").textValue();
					String description = child.path("itemCode").path("description").textValue();//could be null
					String dataSource = child.path("dataSource").textValue();
					// TODO call dataSouce API to get the identifier
					String dataSourceIdentifier = dataSourceService.findDataSourceNameById(dataSource);
					
					String patientLocalId = child.path("patientLocalId").textValue();
					
					retrievePatients.getPatientsRegardingSpecificVitalSigns(patientLocalId, dataSource, vp);
					System.out.println("No: " + i++ + " " + vitalSignId);
					vp.setVitalSignLocalId(vitalSignLocalId);
					vp.setDescription(description);
					vp.setVitalSignId(vitalSignId);;
					vp.setDataSource(dataSource);
					vp.setDataSourceName(dataSourceIdentifier);
					vp.setPatientLocalId(patientLocalId);
					
					vitalSignsPatients.add(vp);
				}
			}
			// write data to CSV file
			writDataToCSV.writePatientVitalSignDataToCSV(vitalSignsPatients);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vitalSignsPatients;
	}
}
