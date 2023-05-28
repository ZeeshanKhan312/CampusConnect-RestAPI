package com.connect.campus.dao;


import com.connect.campus.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,Integer> {
    public TeacherEntity findByTeacherId(int id);
    public TeacherEntity findByTeacherIdAndTeacherPassword(int id, String password);
    @Query(value = "SELECT * FROM teacher_table WHERE teacher_name LIKE %:name%",nativeQuery = true)
    public List<TeacherEntity> findByTeacherName(String name);
}
