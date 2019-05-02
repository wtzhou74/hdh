package com.healthcurrent.data_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.SubjectAlias;

@Repository
public interface SubjectAliasRepository extends JpaRepository<SubjectAlias, Long> {

	List<SubjectAlias> findByAliasAndFacilityKey(String alias, Integer facilityKey);
}
