package com.connect.campus.dao;

import com.connect.campus.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    public StudentEntity findByStudentId(@Param("id") int studentId);

    public StudentEntity findByStudentIdAndPassword(@Param("id") int id, @Param("paswd") String password);
    @Query(value = "SELECT * FROM student_table WHERE student_name LIKE %:name%", nativeQuery = true)
    public List<StudentEntity> findByStudentName(@Param("name") String name);
    @Query(value="SELECT * FROM student_table WHERE batch_id =:bId", nativeQuery = true)
    public List<StudentEntity> findByBatchId(@Param("bId") String batchId);
    @Query(value = "SELECT batch_id FROM student_table WHERE student_id=:sId", nativeQuery = true)
    public String findBatchIdByStudentId(@Param("sId") int studentId);
    @Query(value = "SELECT * FROM student_table WHERE fees_paid=:val", nativeQuery = true)
    public List<StudentEntity> findUnpaidStudents(@Param("val") int val);

    @Query(value = "SELECT parent_id FROM student_table WHERE student_id=:sId", nativeQuery = true)
    public int findParentId(@Param("sId") int id);

}
