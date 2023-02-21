package com.connect.campus.dao;

import com.project.etudiant.entities.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, String>{
public BatchEntity findByBatchId(String id);

}
