package com.healthcurrent.data_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.CodedElement;

@Repository
public interface CodedElementRepository extends JpaRepository<CodedElement, Long>{
	
	List<CodedElement> findByCodedElementKey(Long codedElementKey);
}
