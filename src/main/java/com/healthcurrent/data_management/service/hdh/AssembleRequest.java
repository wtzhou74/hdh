package com.healthcurrent.data_management.service.hdh;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.healthcurrent.data_management.util.ConstantUtil;

@Service
public class AssembleRequest {

	public HttpEntity<String> getRequest() {
		String credentials = ConstantUtil.USERNAME + ":" + ConstantUtil.PASSWORD;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("X-Requested-With", "HDH");
		map.add("Authorization", "Basic " + encodedCredentials);
		HttpEntity<String> request = new HttpEntity<String>(map);
		
		return request;
	}
}
