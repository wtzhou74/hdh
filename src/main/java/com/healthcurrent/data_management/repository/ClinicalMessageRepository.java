package com.healthcurrent.data_management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.ClinicalMessage;

@Repository
public interface ClinicalMessageRepository extends JpaRepository<ClinicalMessage, Long> {

	List<ClinicalMessage> findBySourceKeyAndMessageDateBetween(Long sourceKey, Date startDate, Date endDate);
	
	@Query("select u.messageControlId from ClinicalMessage u where u.sourceKey = ?1 and u.messageDate >= ?2 and u.messageDate < ?3")
	//List<Object[]> findMsgControlId(Long sourceKey, Date startDate, Date endDate);
	List<String> findMsgControlId(Long sourceKey, Date startDate, Date endDate);
	
	List<ClinicalMessage> findBySourceKey(Long sourceKey);
	
	@Query("select u.clinicalMessageKey from ClinicalMessage u where u.clinicalMessageTypeKey = ?1 and u.sourceKey = ?2 and u.receivedDate >= ?3 and u.receivedDate < ?4")
	List<Long> findClinicalMsgKey(Integer clinicalMsgTypeKey, Long sourceKey, Date startDate, Date endDate);
	
	List<ClinicalMessage> findBySourceKeyAndSubjectKeyAndClinicalMessageTypeKey(Integer sourceKey, Long subjectKey, Integer clinicalMessageTypeKey);
}
