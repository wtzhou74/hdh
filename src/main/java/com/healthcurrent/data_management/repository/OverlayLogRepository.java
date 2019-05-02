package com.healthcurrent.data_management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.OverlayLog;

@Repository
public interface OverlayLogRepository extends JpaRepository<OverlayLog, Long>{
	List<OverlayLog> findByMsgDateBetween(Date startDate, Date endDate);
	List<OverlayLog> findTop10ByMsgDateBetween(Date startDate, Date endDate);
	
	@Query("select u.controlId from OverlayLog u where u.msgDate >= ?1 and u.msgDate < ?2")
	List<String> findControlId(Date startDate, Date endDate);
}
