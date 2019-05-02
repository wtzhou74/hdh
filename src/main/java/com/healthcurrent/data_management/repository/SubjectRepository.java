package com.healthcurrent.data_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcurrent.data_management.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
