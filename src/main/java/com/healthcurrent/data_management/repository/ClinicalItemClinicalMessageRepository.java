package com.healthcurrent.data_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.ClinicalItemClinicalMessage;
import com.healthcurrent.data_management.model.ClinicalItemClinicalMessageId;

@Repository
public interface ClinicalItemClinicalMessageRepository 
	extends JpaRepository<ClinicalItemClinicalMessage, ClinicalItemClinicalMessageId> {

	//@Query("select u.clinicalItemKey from ClinicalItemClinicalMessage u where u.entryId.clinicalMessageKey = ?1")
	List<ClinicalItemClinicalMessage> findByEntryIdClinicalMessageKey(Long clinicalMessageKey);
}
