package com.connect.campus.dao;

import com.connect.campus.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Integer > {
    @Query(value = "SELECT * FROM schedule_table WHERE batch_id=:batchId AND day=:day1", nativeQuery = true)
    public ScheduleEntity findByBatchIdAndDay(@Param("batchId")String batch_id, @Param("day1") String day);

    @Query(value = "SELECT * FROM schedule_table WHERE batch_id=:batchId", nativeQuery = true)
    public List<ScheduleEntity> findByBatchId(@Param("batchId")String batch_id);
}
