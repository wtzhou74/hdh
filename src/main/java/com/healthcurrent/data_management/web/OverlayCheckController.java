package com.healthcurrent.data_management.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.healthcurrent.data_management.model.ClinicalItemClinicalMessage;
import com.healthcurrent.data_management.model.ClinicalMessage;
import com.healthcurrent.data_management.model.OverlayLog;
import com.healthcurrent.data_management.repository.ClinicalItemClinicalMessageRepository;
import com.healthcurrent.data_management.repository.ClinicalMessageRepository;
import com.healthcurrent.data_management.repository.OverlayLogRepository;

@RestController
public class OverlayCheckController {

	@Autowired
	private OverlayLogRepository overlayLogRepo;
	
	@Autowired
	private ClinicalMessageRepository clinicalMsgRepo;

	@Autowired
	ClinicalItemClinicalMessageRepository itemMsgRepo;
	
	@RequestMapping(value = "/overlay-log/findByDateRange", method = RequestMethod.GET)
	public List<OverlayLog> findByMsgDate() {
		String startDateStr = "2019-03-31 00:00:00";
		String endDateStr = "2019-04-01 00:00:00";
		
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
		
		//List<ClinicalMessage> clinicalMsgs = clinicalMsgRepo.findBySourceKeyAndMessageDateBetween(1476l, startDate, endDate);
		// List<Object[]> msgControlIds = clinicalMsgRepo.findMsgControlId(1476l, startDate, endDate);
		List<String> msgControlIds = clinicalMsgRepo.findMsgControlId(1476l, startDate, endDate);
		//List<OverlayLog> overlayLogs = overlayLogRepo.findByMsgDateBetween(startDate, endDate);
		List<String> controlIds = overlayLogRepo.findControlId(startDate, endDate);
		int i = 0;
		for (String controlId : controlIds) {
			if (msgControlIds.contains(controlId)) {
				System.out.println("Message Control Id " + i + ": " + controlId);
				i++;
			}
		}
		
		return overlayLogRepo.findByMsgDateBetween(startDate, endDate);
	}
	
//	@RequestMapping(value = "/vorro/findItemKeys", method = RequestMethod.GET)	
//	public void getClinicalItemsKey() {
//		List<ClinicalItemClinicalMessage> itemKeys = itemMsgRepo.findByEntryIdClinicalMessageKey(755163184l);
//		System.out.println("");
//	}
}
