package com.healthcurrent.data_management.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.healthcurrent.data_management.model.ClinicalItem;
import com.healthcurrent.data_management.model.ClinicalItemClinicalMessage;
import com.healthcurrent.data_management.model.ClinicalMessage;
import com.healthcurrent.data_management.model.CodedElement;
import com.healthcurrent.data_management.model.SubjectAlias;
import com.healthcurrent.data_management.model.vorro.CdaClinicalItemModel;
import com.healthcurrent.data_management.model.vorro.DbClinicalItemModel;
import com.healthcurrent.data_management.repository.ClinicalItemClinicalMessageRepository;
import com.healthcurrent.data_management.repository.ClinicalItemRepository;
import com.healthcurrent.data_management.repository.ClinicalMessageRepository;
import com.healthcurrent.data_management.repository.CodedElementRepository;
import com.healthcurrent.data_management.repository.CodedElementUsageRepository;
import com.healthcurrent.data_management.repository.SubjectAliasRepository;
import com.healthcurrent.data_management.util.ConstantUtil;
import com.healthcurrent.data_management.util.XmlParser;

@Service
public class VorroService {

	@Autowired
	XmlParserService xmlParser;
	
	@Autowired
	SubjectAliasRepository subjectAliasRepo;
	
	@Autowired
	ClinicalMessageRepository clinicalMsgRepo;
	
	@Autowired
	ClinicalItemClinicalMessageRepository itemMsgRepo;
	
	@Autowired
	CodedElementRepository codedElementRepo;
	
	@Autowired
	CodedElementUsageRepository codedElemUsageRepo;
	
	@Autowired
	ClinicalItemRepository clinicalItemRepo;
	
	@Autowired
	WriteDataToCSV writeDataToCSV;
	
	/**
	 * Get the message key in table clinical_message regarding the specified file
	 * */
	public Long getMessageKey(File file) {
		
		if (null == file.getName() || file.getName().equals("")) {
			return 0l;
		}
		String oid = file.getName().split("_")[1];
		int facilityKey = 0;
		if (oid.equals(ConstantUtil.ARIZONA_HOME_CARE_OID)) {
			facilityKey = 1400;
		} else if (oid.equals(ConstantUtil.ADVANCED_CLINICAL_ASSOCIATES_OID)) {
			facilityKey = 1487;
		} else if (oid.equals(ConstantUtil.NIGHTINGALE_HOMECARE_OID)) {
			facilityKey = 1363;
		} else if (oid.equals(ConstantUtil.OSBORN_HEALTH_AND_REHAB_OID)) {
			facilityKey = 1407;
		}
		
		List<String> roleIds = new ArrayList<String>();
		String cdaId = "";
		try {
			roleIds = xmlParser.fetchPatientRoleId(file); //get alias number
			cdaId = xmlParser.fetchIdOfCdaFile(file); // get unique id of the cda file
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String effectiveTime = xmlParser.fetchEffectiveTime(file);//get effective time of the file
		
		
		for (String alias : roleIds) {
			
			List<SubjectAlias> subjectAliases =  subjectAliasRepo.findByAliasAndFacilityKey(alias, facilityKey);
			// alias + facilityKey should maps to a unique subject
			if (subjectAliases.size() > 1) {
				System.out.println("mismathced alias and subject, "
						+ "the Alias is " + subjectAliases.get(0).getAlias()
						+ " Facility key is: " + subjectAliases.get(0).getFacilityKey());
				return 0l;
			}
			
			Long subjectKey = subjectAliases.get(0).getSubjectKey();
			System.out.println("Subject Key: " + subjectKey);
			List<ClinicalMessage> clinicalMsgs = clinicalMsgRepo
					.findBySourceKeyAndSubjectKeyAndClinicalMessageTypeKey(facilityKey, subjectKey, ConstantUtil.CCDA_MESSAGE_KEY);
			for (ClinicalMessage msg : clinicalMsgs) {
				// message control id in DB is the id of ccda file
				if (msg.getMessageControlId() != null && msg.getMessageControlId().equals(cdaId)) {
					// TODO check all available messages
					return msg.getClinicalMessageKey();
				}
			}
		}
		
		return 0l;
	}
	
	/**
	 * Get a list of message keys of files within a specified directory
	 * **/
	public Map<String, Long> getMessageKeys(String path) {
		File folder = new File(path);// TODO test Sample 10
		//List<Long> messageKeys = new ArrayList<Long>();
		Map<String, Long> fileMsgMap = new HashMap<>();
		try {
			for (File file : folder.listFiles()) {
				System.out.println(file.getName());
				
				Long messageKey = getMessageKey(file);
				if (messageKey != 0) {
					fileMsgMap.put(file.getName(), messageKey);
					//messageKeys.add(messageKey);
					System.out.println("Message Key: " + messageKey);
				}
				System.out.println("---------------------------------------------");
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileMsgMap;
	}
	
	/**
	 * Extract clinical items from c-cda files
	 * */
	public void extractClinicalItemsFromCda (String path) {
		File folder = new File(path);// TODO test Sample 10

		List<CdaClinicalItemModel> cdaItemModels = new ArrayList<CdaClinicalItemModel>();
		try {
			for (File file : folder.listFiles()) {
				System.out.println(file.getName());
				xmlParser.analyzeCdaFromVorro(file, cdaItemModels);
			
				System.out.println("---------------------------------------------");
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// write statistical to csv file
		writeDataToCSV.writeStatisticalVorroDataToCSV(cdaItemModels);
	}
	
//	public static void main(String[] args) {
//		VorroService ser = new VorroService();
//		ser.extractClinicalItemsFromCda();
//	}
	
	/**
	 * Get codedElements from DB for specified message keys
	 * */
	public void getCodedElem (Map<String, Long> fileMsgKey) {
		List<DbClinicalItemModel> dbClinicalItemModel = new ArrayList<DbClinicalItemModel>();
		for (String fileName : fileMsgKey.keySet()) {
			
			System.out.println("Message Key: " + fileMsgKey.get(fileName));
			// get item keys
			List<ClinicalItemClinicalMessage> itemKeys = itemMsgRepo.findByEntryIdClinicalMessageKey(fileMsgKey.get(fileName));
			List<String> conceptCodes = new ArrayList<String>();
			Map<String, Integer> codedElemUsageMap = new HashMap<>();
			int itemCounter = 0;
			for (ClinicalItemClinicalMessage cicm : itemKeys) {
				List<ClinicalItem> clinicalItems = clinicalItemRepo.findByClinicalItemKey(cicm.getEntryId().getClinicalItemKey());
				for (ClinicalItem item : clinicalItems) {

					if (item.getCodeKey() == null) {
						continue;// code_key of encounter in DB is null
					}
					System.out.println("Number: " + itemCounter++ + " Code_key: " + item.getCodeKey());
					List<CodedElement> codedElements = codedElementRepo.findByCodedElementKey(item.getCodeKey());
					
					for (CodedElement element : codedElements) {
						String elemUsageLabel =
								codedElemUsageRepo.findByCodedElemUsageKey(element.getCodedElemUsageKey()).get(0).getLabel();
						conceptCodes.add(element.getCode());
						
						Integer value = codedElemUsageMap.putIfAbsent(elemUsageLabel, codedElemUsageMap.getOrDefault(elemUsageLabel, 0) + 1);
						if (value != null) codedElemUsageMap.put(elemUsageLabel, value + 1);
						System.out.println(element.getCode() + "   " + element.getLabel() + "  " + elemUsageLabel);
					}
				}
			}
			for (Map.Entry<String, Integer> entry : codedElemUsageMap.entrySet()) {
				DbClinicalItemModel dbClinicalItem = new DbClinicalItemModel();
				dbClinicalItem.setFileName(fileName.split("_")[2]);
				dbClinicalItem.setSourceName(dataSourceName(fileName));
				dbClinicalItem.setCodedElemUsage(entry.getKey());
				dbClinicalItem.setNumOfElem(entry.getValue());
				dbClinicalItemModel.add(dbClinicalItem);
			}
			//dbClinicalItem.setCodedElemUsageMap(codedElemUsageMap);
			//dbClinicalItemModel.add(dbClinicalItem);
			System.out.println("*********************************************");
		}
		
		// write data to CSV
		writeDataToCSV.writeStatisticalVorroDbDataToCSV(dbClinicalItemModel);
	}
	
	public String dataSourceName(String fileName) {
		String sourceName = "";
		if (null == fileName || fileName.equals("")) {
			return "";
		}
		String oid = fileName.split("_")[1];
		if (oid.equals(ConstantUtil.ARIZONA_HOME_CARE_OID)) {
			sourceName = ConstantUtil.ARIZONA_HOME_CARE_NAME;
		} else if (oid.equals(ConstantUtil.ADVANCED_CLINICAL_ASSOCIATES_OID)) {
			sourceName = ConstantUtil.ADVANCED_CLINICAL_ASSOCIATES_NAME;
		} else if (oid.equals(ConstantUtil.NIGHTINGALE_HOMECARE_OID)) {
			sourceName = ConstantUtil.NIGHTINGALE_HOMECARE_NAME;
		} else if (oid.equals(ConstantUtil.OSBORN_HEALTH_AND_REHAB_OID)) {
			sourceName = ConstantUtil.OSBORN_HEALTH_AND_REHAB_NAME;
		}
		
		return sourceName;
	}
}
