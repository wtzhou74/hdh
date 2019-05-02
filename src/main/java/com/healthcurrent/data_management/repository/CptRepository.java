package com.healthcurrent.data_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.CPT;

@Repository
public interface CptRepository extends JpaRepository<CPT, Long>{

	List<CPT> getCptByConceptKey(Long conceptKey);
}
