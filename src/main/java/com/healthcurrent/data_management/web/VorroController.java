package com.healthcurrent.data_management.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.healthcurrent.data_management.model.ClinicalItem;
import com.healthcurrent.data_management.model.ClinicalItemClinicalMessage;
import com.healthcurrent.data_management.model.ClinicalMessage;
import com.healthcurrent.data_management.model.CodedElement;
import com.healthcurrent.data_management.repository.ClinicalItemClinicalMessageRepository;
import com.healthcurrent.data_management.repository.ClinicalItemRepository;
import com.healthcurrent.data_management.repository.ClinicalMessageRepository;
import com.healthcurrent.data_management.repository.CodedElementUsageRepository;
import com.healthcurrent.data_management.service.VorroService;
import com.healthcurrent.data_management.repository.CodedElementRepository;

@RestController
@RequestMapping("/vorro")
public class VorroController {

	@Autowired
	ClinicalMessageRepository clincalMsgRepo;
	
	@Autowired
	ClinicalMessageRepository clinicalMsgRepo;

	@Autowired
	ClinicalItemClinicalMessageRepository itemMsgRepo;
	
	@Autowired
	ClinicalItemRepository clinicalItemRepo;
	
	@Autowired
	CodedElementRepository codedElementRepo;
	
	@Autowired
	CodedElementUsageRepository codedElemUsageRepo;
	
	@Autowired
	VorroService vorroService;
	
	/**
	 * Find specific messages with souce key
	 * */
	@RequestMapping(value = "/getClinicalMsg/{sourceKey}", method = RequestMethod.GET)
	public void getVorroClinicalMsgs (@PathVariable Long sourceKey) {
		List<ClinicalMessage> msgs = clincalMsgRepo.findBySourceKey(sourceKey);
		System.out.println("Done!");
	}
	
	@RequestMapping(value = "/findItemKeys", method = RequestMethod.GET)	
	public void getClinicalItemsKey() {
		List<ClinicalItemClinicalMessage> itemKeys = itemMsgRepo.findByEntryIdClinicalMessageKey(755163184l);
		System.out.println("");
	}
	
	/**
	 * Fetch clinical items by given the message key
	 * */
	@RequestMapping(value = "/findItems/{msgKey}", method = RequestMethod.GET)
	public void getClinicalItems(@PathVariable Long msgKey) {
		// get item keys
		List<ClinicalItemClinicalMessage> itemKeys = itemMsgRepo.findByEntryIdClinicalMessageKey(msgKey);
		List<String> conceptCodes = new ArrayList<String>();
		for (ClinicalItemClinicalMessage cicm : itemKeys) {
			List<ClinicalItem> clinicalItems = clinicalItemRepo.findByClinicalItemKey(cicm.getEntryId().getClinicalItemKey());
			for (ClinicalItem item : clinicalItems) {
				List<CodedElement> codedElements = codedElementRepo.findByCodedElementKey(item.getCodeKey());
				
				for (CodedElement element : codedElements) {
					// remove duplicates
					if (conceptCodes.size() == 0 || !conceptCodes.contains(element.getCode())) {
						// find elem usage label
						String elemUsageLabel =
								codedElemUsageRepo.findByCodedElemUsageKey(element.getCodedElemUsageKey()).get(0).getLabel();
						conceptCodes.add(element.getCode());
						System.out.println(element.getCode() + "   " + element.getLabel() + "  " + elemUsageLabel);
					}
				}
			}
		}
		
	}
	
	/**
	 * Fetch clinical items by given fileSource, e.g. DB, CDA file, the source is DB by default
	 * */
	@RequestMapping(value = "/findItems/data-source/{fileSource}", method = RequestMethod.GET)
	public void getClinicalItems(@PathVariable int fileSource) {
		String path = "C:/Users/WentaoZhou/Documents/Projects/VORRO/sftp/testWithName";
		// analyze cda file
		if (fileSource == 1) {
			vorroService.extractClinicalItemsFromCda(path);
		} else {
			// check items in DB
			// List<Long> messageKeys = vorroService.getMessageKeys(path);
			Map<String, Long> fileMsgMap = vorroService.getMessageKeys(path);
			vorroService.getCodedElem(fileMsgMap);
		}
		
	}
	
	/*
	 * Calculate the number of vorro message based on msgType, e.g. HL7, CCD and souce key, e.g. 1400 for Arizona Home Care etc.
	 * 
	 * */
	@RequestMapping(value = "/calculateMsg/{msgType}/{souceKey}", method = RequestMethod.GET)
	public void calculateReceivedMessage(@PathVariable Integer msgType, @PathVariable Long souceKey) {
		String startDateStr = "2019-04-01 00:00:00";
		String endDateStr = "2019-04-02 00:00:00";
		
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
		
		int count = 0;
		for (int i = 0; i < 20; i++) {
			List<Long> msgs = clinicalMsgRepo.findClinicalMsgKey(msgType, souceKey, startDate, endDate);
			startDate = DateUtils.addDays(startDate, 1);
			endDate = DateUtils.addDays(endDate, 1);
			count += msgs.size();
			System.out.println("Day " + i + ": " + msgs.size());
		}
		//List<Long> msgs = clinicalMsgRepo.findClinicalMsgKey(3, 1400l, startDate, endDate);
		System.out.println(count);
	}
}
