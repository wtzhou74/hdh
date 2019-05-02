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
import com.healthcurrent.data_management.model.test.VitalSignsPatient;
import com.healthcurrent.data_management.service.WriteDataToCSV;

@Service
public class DataSourceService {

	@Autowired
	AssembleRequest assembleRequest;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${hdh.host}")
	String host;
	
	@Value("${hdh.baseUrl}")
	String baseUrl;
	
	@Value("${hdh.apiVersion}")
	String apiVersion;
	
	@Value("${hdh.endpoint.dataSource}")
	String dataSource;
	
	public String findDataSourceNameById (String id) {
		HttpEntity<String> request = assembleRequest.getRequest();
		String fullUrl = host + baseUrl + apiVersion + dataSource + "/" + id;
		ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, request, String.class);

		String dataSourceName = "";
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(response.getBody());
			dataSourceName = root.path("dataSourceName").textValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSourceName;
	}
}
