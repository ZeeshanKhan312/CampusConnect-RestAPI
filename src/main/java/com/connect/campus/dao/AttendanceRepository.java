package com.connect.campus.dao;

import com.connect.campus.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity,String> {
    @Query(value = "SELECT * FROM attendance_table WHERE student_id=:studentId AND subject_id=:subjectId ",nativeQuery = true)
    public List<AttendanceEntity> findByStudentIdAndSubjectId(@Param("studentId")int studentId,@Param("subjectId")int subjectId);
}
