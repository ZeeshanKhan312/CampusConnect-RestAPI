package com.connect.campus.dao;

import com.connect.campus.entities.StudentProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentProgressRepository extends JpaRepository<StudentProgressEntity, String > {
    public StudentProgressEntity findByProgressId(String id);
    @Query(value = "SELECT * FROM student_progress_table WHERE batch_id=:batchId AND subject_id=:subjectId ",nativeQuery = true)
    public List<StudentProgressEntity> findByBatchIdAndSubjectId(@Param("batchId")String batchId, @Param("subjectId") int subjectId);
}
