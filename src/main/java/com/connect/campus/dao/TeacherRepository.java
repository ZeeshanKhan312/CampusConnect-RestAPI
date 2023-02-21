package com.connect.campus.dao;

import com.project.etudiant.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,Integer> {
    public TeacherEntity findByTeacherId(int id);
}
