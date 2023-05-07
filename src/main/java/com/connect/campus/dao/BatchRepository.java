package com.connect.campus.dao;

import com.connect.campus.entities.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, String>{
    public BatchEntity findByBatchId(String id);
    @Query(value = "SELECT current_semester FROM batch_table WHERE batch_id=:batchId", nativeQuery = true)
    public String findByCurrentSemester(@Param("batchId") String batchId);
}
