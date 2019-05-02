package com.healthcurrent.data_management.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcurrent.data_management.model.test.ImmunizationPatient;
import com.healthcurrent.data_management.model.test.ProcedurePatient;
import com.healthcurrent.data_management.model.test.VitalSignsPatient;
import com.healthcurrent.data_management.model.vorro.CdaClinicalItemModel;
import com.healthcurrent.data_management.model.vorro.DbClinicalItemModel;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Service
public class WriteDataToCSV {

	private static final String PATIENT_PROCEDURE_FILE = "./patient-procedure-sample1.csv";
	private static final String PATIENT_VITAL_SIGN_FILE = "./patient-vital-signs-sample1.csv";
	private static final String PATIENT_IMMUNIZATION_FILE = "./patient-immunization-sample1.csv";
	private static final String VORRO_CDA_STAT_FILE = "./vorro-cda-stat-file.csv";
	private static final String VORRO_DB_STAT_FILE = "./vorro-db-stat-file.csv";
	
	public void writePatientProcedureDataToCSV(List<ProcedurePatient> pp) {
		
		try { 
			  
            // Creating writer class to generate 
            // csv file 
            FileWriter writer = new 
                       FileWriter(PATIENT_PROCEDURE_FILE); 
  
            // create a list of employee 
             
  
            // Create Mapping Strategy to arrange the  
            // column name in order 
            ColumnPositionMappingStrategy mappingStrategy= 
                        new ColumnPositionMappingStrategy(); 
            mappingStrategy.setType(ProcedurePatient.class); 
  
            // Arrange column name as provided in below array. 
            String[] columns = new String[]  
                    { "procedureId", "procedureLocalId", "description", "dataSource", "dataSourceName", "patientId", "patientLocalId", "dob", "first", "middle", "last" }; 
            mappingStrategy.setColumnMapping(columns); 
  
            // Createing StatefulBeanToCsv object 
            StatefulBeanToCsvBuilder<ProcedurePatient> builder= 
                        new StatefulBeanToCsvBuilder(writer); 
            StatefulBeanToCsv beanWriter =  
          builder.withMappingStrategy(mappingStrategy).build(); 
  
            // Write list to StatefulBeanToCsv object 
            beanWriter.write(pp); 
  
            // closing the writer object 
            writer.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void writePatientVitalSignDataToCSV(List<VitalSignsPatient> vp) {
		
		try { 
			  
            // Creating writer class to generate 
            // csv file 
            FileWriter writer = new 
                       FileWriter(PATIENT_VITAL_SIGN_FILE); 
             
  
            // Create Mapping Strategy to arrange the  
            // column name in order 
			ColumnPositionMappingStrategy mappingStrategy= 
                        new ColumnPositionMappingStrategy(); 
            mappingStrategy.setType(VitalSignsPatient.class); 
  
            // Arrange column name as provided in below array. 
            String[] columns = new String[]  
                    { "vitalSignId", "vitalSignLocalId", "description", "dataSource", "dataSourceName", "patientId", "patientLocalId", "dob", "first", "middle", "last" }; 
            mappingStrategy.setColumnMapping(columns); 
  
            // Creating StatefulBeanToCsv object 
			StatefulBeanToCsvBuilder<VitalSignsPatient> builder= 
                        new StatefulBeanToCsvBuilder(writer); 
            StatefulBeanToCsv beanWriter =  
          builder.withMappingStrategy(mappingStrategy).build(); 
  
            // Write list to StatefulBeanToCsv object 
            beanWriter.write(vp); 
  
            // closing the writer object 
            writer.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void writePatientImmunizationDataToCSV(List<ImmunizationPatient> ip) {
		
		try { 
			  
            // Creating writer class to generate 
            // csv file 
            FileWriter writer = new 
                       FileWriter(PATIENT_IMMUNIZATION_FILE); 
             
  
            // Create Mapping Strategy to arrange the  
            // column name in order 
			ColumnPositionMappingStrategy mappingStrategy= 
                        new ColumnPositionMappingStrategy(); 
            mappingStrategy.setType(ImmunizationPatient.class); 
  
            // Arrange column name as provided in below array. 
            String[] columns = new String[]  
                    { "immunizationId", "immunizationLocalId","description", "dataSource", "dataSourceName", "patientId", "patientLocalId", "dob", "first", "middle", "last" }; 
            mappingStrategy.setColumnMapping(columns); 
  
            // Creating StatefulBeanToCsv object 
			StatefulBeanToCsvBuilder<ImmunizationPatient> builder= 
                        new StatefulBeanToCsvBuilder(writer); 
            StatefulBeanToCsv beanWriter =  
          builder.withMappingStrategy(mappingStrategy).build(); 
  
            // Write list to StatefulBeanToCsv object 
            beanWriter.write(ip); 
  
            // closing the writer object 
            writer.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void writeStatisticalVorroDataToCSV(List<CdaClinicalItemModel> cdaItems) {
		
		try { 
			  
            // Creating writer class to generate 
            // csv file 
            FileWriter writer = new 
                       //FileWriter(VORRO_CDA_STAT_FILE, true);// set True, then the records will be appended to existing file
            			FileWriter(VORRO_CDA_STAT_FILE);
             
            // Create Mapping Strategy to arrange the  
            // column name in order 
			ColumnPositionMappingStrategy mappingStrategy= 
                        new ColumnPositionMappingStrategy(); 
            mappingStrategy.setType(CdaClinicalItemModel.class); 
  
            // Arrange column name as provided in below array. 
            String[] columns = new String[]  
                    { "fileId", "sourceName", "title", "numOfCodedEntry", "numOfMissingCodeEntry", "numOfProblemEntry" }; 
            mappingStrategy.setColumnMapping(columns); 
  
            // Creating StatefulBeanToCsv object 
			StatefulBeanToCsvBuilder<CdaClinicalItemModel> builder= 
                        new StatefulBeanToCsvBuilder(writer); 
            StatefulBeanToCsv beanWriter =  
            builder.withMappingStrategy(mappingStrategy).build(); 
  
            // Write list to StatefulBeanToCsv object 
            beanWriter.write(cdaItems); 
  
            // closing the writer object 
            writer.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void writeStatisticalVorroDbDataToCSV(List<DbClinicalItemModel> dbItems) {
		
		try { 
			  
            // Creating writer class to generate 
            // csv file 
            FileWriter writer = new 
                       FileWriter(VORRO_DB_STAT_FILE); 
             
  
            // Create Mapping Strategy to arrange the  
            // column name in order 
			ColumnPositionMappingStrategy mappingStrategy= 
                        new ColumnPositionMappingStrategy(); 
            mappingStrategy.setType(DbClinicalItemModel.class); 
  
            // Arrange column name as provided in below array. 
            String[] columns = new String[]  
                    { "fileId", "sourceName", "codedElemUsage", "numOfElem" }; 
            mappingStrategy.setColumnMapping(columns); 
  
            // Creating StatefulBeanToCsv object 
			StatefulBeanToCsvBuilder<DbClinicalItemModel> builder= 
                        new StatefulBeanToCsvBuilder(writer); 
//            StatefulBeanToCsv beanWriter =  
//            builder.withMappingStrategy(mappingStrategy).build(); 
			StatefulBeanToCsv beanWriter = builder.build();
  
            // Write list to StatefulBeanToCsv object 
            beanWriter.write(dbItems); 
  
            // closing the writer object 
            writer.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}
