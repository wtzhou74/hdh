package com.healthcurrent.data_management.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.healthcurrent.data_management.model.ClinicalItem;
import com.healthcurrent.data_management.model.ClinicalItemClinicalMessage;
import com.healthcurrent.data_management.repository.ClinicalItemClinicalMessageRepository;

public class VorroTest {

	@Autowired
	ClinicalItemClinicalMessageRepository itemMsgRepo;
	
	public void getClinicalItemsKey() {
		//List<ClinicalItemClinicalMessage> itemKeys = itemMsgRepo.findByclinicalMessageKey(755163184l);
		System.out.println("");
	}
	
}
