package com.healthcurrent.data_management.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

import com.healthcurrent.data_management.model.vorro.CdaClinicalItemModel;

@Service
public class XmlParserService {

	@Autowired
	WriteDataToCSV writeDataToCSV;
	
	@Autowired
	VorroService vorroService;
	
	private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	public NodeList fetchNodeList (File file, String pathExp) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();	
		System.out.println("FileName: " + file.getName());
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(pathExp);
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		return nl;
	}
	
	public String fetchEffectiveTime(File file) throws Exception {
		
		String pathExp = "/ClinicalDocument/effectiveTime";
		NodeList nl = fetchNodeList(file, pathExp);
		String effectiveTime = "";
		if (nl != null && nl.getLength() > 0) {
			Element e = (Element)nl.item(0);
			effectiveTime = e.getAttribute("value");
			System.out.println("Effective Time: " + effectiveTime);
		}
		return effectiveTime;
	}
	
	public String fetchIdOfCdaFile(File file) throws Exception {
		
		String pathExp = "/ClinicalDocument/id";
		NodeList nl = fetchNodeList(file, pathExp);
		String id = "";
		if (nl != null && nl.getLength() > 0) {
			Element e = (Element)nl.item(0);
			id = e.getAttribute("extension");
			System.out.println("CCDA File ID is : " + id);
		}
		return id;
	}
	
	public List<String> fetchPatientRoleId (File file) throws Exception{
		
		String pathExp = "/ClinicalDocument/recordTarget/patientRole/id";
		NodeList nl = fetchNodeList(file, pathExp);
		List<String> patientRoleIds = new ArrayList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			Element idElem = (Element)nl.item(i);
			String id = idElem.getAttribute("extension");
			System.out.println("ID EXTENSION: " + id);
				patientRoleIds.add(id);
		}
		return patientRoleIds;
	}
	
	/**
	 * Analyze the CCDA file based on the follow logic
	 * - Extract items from both tag CODE and tag VALUE for each entry
	 * - Ignore the item whose displayName="Problem" or Code="55607006"
	 * */
	public void analyzeCdaFromVorro(File file, List<CdaClinicalItemModel> cdaItemModels) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();	
		System.out.println("FileName: " + file.getName());
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("/ClinicalDocument/component/structuredBody/component/section");
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		//NodeList nList = doc.getElementsByTagName("component");
		for (int i = 0; i < nl.getLength(); i++) {
			Element node = (Element)nl.item(i);
			Node title = node.getElementsByTagName("title").item(0);
			if (null != title) {
				String titleTxt = title.getTextContent();
				System.out.println(titleTxt + "---------------");
				CdaClinicalItemModel cdaItemModel = new CdaClinicalItemModel();
				//cdaItemModel.setFileName(file.getName());
				cdaItemModel.setFileId(file.getName().split("_")[2]);
				cdaItemModel.setSourceName(vorroService.dataSourceName(file.getName()));
				cdaItemModel.setTitle(titleTxt);
				NodeList entries = node.getElementsByTagName("entry");				
				for (int j = 0; j < entries.getLength(); j++) {
					Element entry = (Element)entries.item(j);
					// identify the coded element
					NodeList entryRelationship = entry.getElementsByTagName("entryRelationship");
					Element entryRelationshipElem = null;
					// some of code/value are not included in tag EntryRelationship
					if (entryRelationship.item(0) != null) {
						entryRelationshipElem = (Element) entryRelationship.item(0);// For each entry, there is only one entryRelationship
					} else {
						entryRelationshipElem = entry;
					}
					// analyze code tag
					fetchCodeOrValueItem(entryRelationshipElem, "code", cdaItemModel);
					// analyze value tag
					fetchCodeOrValueItem(entryRelationshipElem, "value", cdaItemModel);					
				}
				cdaItemModels.add(cdaItemModel);
				System.out.println(titleTxt + " includes: " + cdaItemModel.getNumOfCodedEntry() + " items");
				System.out.println("Number of Items whose displayName is PROBLEM: " + cdaItemModel.getNumOfProblemEntry());
				System.out.println("Number of Unspecified Items: " + cdaItemModel.getNumOfMissingCodeEntry());
				System.out.println("*************************************");
			}

		}
		
	}
	
	/**
	 * Extract code or value item from element EntryRelationshipElem
	 * */
	public void fetchCodeOrValueItem (Element entryRelationshipElem, String tag, CdaClinicalItemModel clinicalItemModel) {
		NodeList codes = entryRelationshipElem.getElementsByTagName(tag);
		for (int k = 0; k < codes.getLength(); k++) {
			Element code = (Element) codes.item(k);
//			String displayName = code.getAttribute("displayName");
//			String codeNum = code.getAttribute("code");
			String displayName = code.hasAttribute("displayName") ? code.getAttribute("displayName") : null;
			String codeNum = code.hasAttribute("code") ? code.getAttribute("code") : null;
			if (displayName == null || codeNum == null) {
				continue;
			}
			if (codeNum.equals("") && displayName.equals("")) {
				clinicalItemModel.setNumOfMissingCodeEntry(clinicalItemModel.getNumOfMissingCodeEntry() + 1);
				continue;
			} else if (codeNum.equals("55607006") || displayName.equalsIgnoreCase("Problem")) {
				clinicalItemModel.setNumOfProblemEntry(clinicalItemModel.getNumOfProblemEntry() + 1);
				continue;
			}
			clinicalItemModel.setNumOfCodedEntry(clinicalItemModel.getNumOfCodedEntry() + 1);
		    System.out.println(codeNum + "    " + displayName);
		}
	}
}
