package com.healthcurrent.data_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcurrent.data_management.model.CodedElementUsage;

public interface CodedElementUsageRepository extends JpaRepository<CodedElementUsage, Integer> {

	List<CodedElementUsage> findByCodedElemUsageKey(Integer elemUsageKey);
}
