package com.healthcurrent.data_management.model.vorro;

import java.util.HashMap;
import java.util.Map;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class DbClinicalItemModel {

	@CsvBindByName(column = "FileName", required = true)
	@CsvBindByPosition(position = 0)
	public String fileName;
	
	@CsvBindByName(column = "FileId", required = true)
	@CsvBindByPosition(position = 0)
	public String fileId;
	
	//public Map<String, Integer> codedElemUsageMap = new HashMap<>();
	@CsvBindByName(column = "SourceName")
	@CsvBindByPosition(position = 1)
	
	public String sourceName;
	@CsvBindByName(column = "CodedElemUsage")
	@CsvBindByPosition(position = 2)
	
	public String codedElemUsage;
	@CsvBindByName(column = "NumOfElem")
	@CsvBindByPosition(position = 3)
	public int numOfElem;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
//	public Map<String, Integer> getCodedElemUsageMap() {
//		return codedElemUsageMap;
//	}
//	public void setCodedElemUsageMap(Map<String, Integer> codedElemUsageMap) {
//		this.codedElemUsageMap = codedElemUsageMap;
//	}
	
	public String getCodedElemUsage() {
		return codedElemUsage;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public void setCodedElemUsage(String codedElemUsage) {
		this.codedElemUsage = codedElemUsage;
	}
	public int getNumOfElem() {
		return numOfElem;
	}
	public void setNumOfElem(int numOfElem) {
		this.numOfElem = numOfElem;
	}
	
	
	
	
}
