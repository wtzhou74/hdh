package com.healthcurrent.data_management.model.vorro;

import java.util.ArrayList;
import java.util.List;

public class CdaClinicalItemModel {

	public String fileName;
	public String fileId;
	public String sourceName;
	public String title;
	public int numOfCodedEntry = 0;
	public int numOfMissingCodeEntry = 0;
	public int numOfProblemEntry = 0;
	
	
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNumOfCodedEntry() {
		return numOfCodedEntry;
	}
	public void setNumOfCodedEntry(int numOfCodedEntry) {
		this.numOfCodedEntry = numOfCodedEntry;
	}
	public int getNumOfMissingCodeEntry() {
		return numOfMissingCodeEntry;
	}
	public void setNumOfMissingCodeEntry(int numOfMissingCodeEntry) {
		this.numOfMissingCodeEntry = numOfMissingCodeEntry;
	}
	public int getNumOfProblemEntry() {
		return numOfProblemEntry;
	}
	public void setNumOfProblemEntry(int numOfProblemEntry) {
		this.numOfProblemEntry = numOfProblemEntry;
	}
	
	
}
