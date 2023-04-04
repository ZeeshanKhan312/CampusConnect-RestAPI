package com.connect.campus.dao;

import com.connect.campus.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Query(value = "SELECT * FROM student_table WHERE student_id =:id", nativeQuery = true)
    public StudentEntity findByStudentId(@Param("id") int studentId);
    @Query(value = "SELECT * FROM student_table WHERE student_name =:name", nativeQuery = true)
    public List<StudentEntity> findByStudentName(@Param("name") String name);
    @Query(value="SELECT * FROM student_table WHERE batch_id =:bId", nativeQuery = true)
    public List<StudentEntity> findByBatchId(@Param("bId") String batchId);
    @Query(value = "SELECT * FROM student_table WHERE student_id =:id AND password=:paswd", nativeQuery = true)
    public StudentEntity findByStudentIdAndPassword(@Param("id") int id, @Param("paswd") String password);
}
