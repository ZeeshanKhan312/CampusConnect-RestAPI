package com.connect.campus.dao;

import com.connect.campus.entities.ExamScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamScheduleRepository extends JpaRepository<ExamScheduleEntity, Integer> {
    @Query(value = "SELECT * FROM exam_schedule WHERE batch_id=:batchId", nativeQuery = true)
    public List<ExamScheduleEntity> findByExamSchedule(@Param("batchId") String batchId);

}
