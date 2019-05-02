package com.healthcurrent.data_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcurrent.data_management.model.ClinicalItem;

public interface ClinicalItemRepository extends JpaRepository<ClinicalItem, Long> {

	List<ClinicalItem> findByClinicalItemKey(Long clinicalItemKey);
	
}
