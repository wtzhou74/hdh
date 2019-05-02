package com.healthcurrent.data_management.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlParser {

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
}
