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
    public List<TeacherEntity> findByTeacherName(String name);
}
