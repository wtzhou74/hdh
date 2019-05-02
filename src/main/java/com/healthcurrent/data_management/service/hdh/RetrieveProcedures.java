package com.healthcurrent.data_management.service.hdh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.healthcurrent.data_management.model.test.ProcedurePatient;
import com.healthcurrent.data_management.service.WriteDataToCSV;

@Service
public class RetrieveProcedures {

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
	
	@Value("${hdh.endpoint.procedures}")
	String procedures;
	
	public List<ProcedurePatient> getProcedures() {
		HttpEntity<String> request = assembleRequest.getRequest();
		ResponseEntity<String> response = restTemplate.exchange(host + baseUrl + apiVersion + procedures, HttpMethod.GET, request, String.class);

		List<ProcedurePatient> procedurePatients = new ArrayList<ProcedurePatient>();
		
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
					ProcedurePatient pp = new ProcedurePatient();
					String procedureId = child.path("id").textValue();
					String procedureLocalId = child.path("localId").textValue();
					String description = child.path("description").textValue();
					String dataSource = child.path("dataSource").textValue();
					// TODO call dataSouce API to get the identifier
					String dataSourceIdentifier = dataSourceService.findDataSourceNameById(dataSource);
					String patientLocalId = child.path("patientLocalId").textValue();
					
					// TODO call patient API to get patient data
					
					retrievePatients.getPatientsRegardingSpecificProcedures(patientLocalId, dataSource, pp);
					System.out.println("No: " + i++ + procedureId);
					pp.setDescription(description);
					pp.setProcedureLocalId(procedureLocalId);
					pp.setProcedureId(procedureId);
					pp.setDataSource(dataSource);
					pp.setDataSourceName(dataSourceIdentifier);
					pp.setPatientLocalId(patientLocalId);
					
					procedurePatients.add(pp);
				}
			}
			// write data to CSV file
			writDataToCSV.writePatientProcedureDataToCSV(procedurePatients);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return procedurePatients;
	}
}
