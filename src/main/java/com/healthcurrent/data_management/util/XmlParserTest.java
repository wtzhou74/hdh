package com.healthcurrent.data_management.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParserTest {

	private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	public static void domXmlParse (File file) throws Exception{
		//File inputFile = new File(filePath);
		//DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();	
		System.out.println("FileName: " + file.getName());
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("/ClinicalDocument/component/structuredBody/component/section");
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		int obsCounter = 0;
		int unspecifiedCodeCounter = 0;
		//NodeList nList = doc.getElementsByTagName("component");
		for (int i = 0; i < nl.getLength(); i++) {
			Element node = (Element)nl.item(i);
			Node title = node.getElementsByTagName("title").item(0);
			if (null != title) {
				String titleTxt = title.getTextContent();
				System.out.println(titleTxt + "---------------");
				int count = 0;
				NodeList entries = node.getElementsByTagName("entry");				
				for (int j = 0; j < entries.getLength(); j++) {
					Element entry = (Element)entries.item(j);
					// calculate the observation item
					NodeList obsNode = entry.getElementsByTagName("observation");
					if (obsNode.getLength() > 0) {
						obsCounter += obsNode.getLength();
					}
					// identify the coded element
					NodeList codes = entry.getElementsByTagName("value");
					for (int k = 0; k < codes.getLength(); k++) {
						Element code = (Element) codes.item(k);
						String displayName = code.getAttribute("displayName");
						String codeNum = code.getAttribute("code");
						if (codeNum.equals("") && displayName.equals("")) {
							unspecifiedCodeCounter++;
							continue;
						}
						count++;
					    System.out.println(codeNum + "    " + displayName);
					}
				}
				System.out.println(titleTxt + " includes: " + count + " items");
				System.out.println("*************************************");
			}

		}
		System.out.println("Number of Obs: " + obsCounter);
		System.out.println("Number of Unspecified Items: " + unspecifiedCodeCounter);
	}
	
	public static void fetchEffectiveTime(File file) throws Exception {
		
		// DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		Document doc = dBuilder.parse(file);
//		doc.getDocumentElement().normalize();	
//		System.out.println("FileName: " + file.getName());
//		
//		XPathFactory xPathfactory = XPathFactory.newInstance();
//		XPath xpath = xPathfactory.newXPath();
//		XPathExpression expr = xpath.compile("/ClinicalDocument/effectiveTime");
//		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		String pathExp = "/ClinicalDocument/effectiveTime";
		NodeList nl = fetchNodeList(file, pathExp);
		if (nl != null && nl.getLength() > 0) {
			Element e = (Element)nl.item(0);
			String effectiveTime = e.getAttribute("value");
			System.out.println("Effective Time: " + effectiveTime);
		}
		
		
	}
	
	public static void fetchPatientRoleId (File file) throws Exception{
		//Get patient role id
//		XPathExpression expr1 = xpath.compile("/ClinicalDocument/recordTarget/patientRole/id");
//		NodeList idNodes = (NodeList) expr1.evaluate(doc, XPathConstants.NODESET);
		String pathExp = "/ClinicalDocument/recordTarget/patientRole/id";
		NodeList nl = fetchNodeList(file, pathExp);
		List<String> patientRoleIds = new ArrayList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			Element idElem = (Element)nl.item(i);
			String id = idElem.getAttribute("extension");
			System.out.println("ID EXTENSION: " + id);
				patientRoleIds.add(id);
			}
	}
	
	public static NodeList fetchNodeList (File file, String pathExp) throws Exception {
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
	
	public static String fetchIdOfCdaFile(File file) throws Exception {
		
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
	
	public static void main(String[] args) {
		//String filePath = "C:\\Users\\WentaoZhou\\Documents\\Projects\\VORRO\\test\\1800\\1800.xml";
		File folder = new File("C:/Users/WentaoZhou/Documents/Projects/VORRO/sftp/test");// TODO test Sample 10
		if (null == folder) return;
		try {
			for (File file : folder.listFiles()) {
				//domXmlParse(file);
				fetchIdOfCdaFile(file);
				fetchEffectiveTime(file);
				fetchPatientRoleId(file);
				System.out.println("---------------------------------------------");
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
