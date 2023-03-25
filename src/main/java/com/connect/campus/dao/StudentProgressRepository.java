package com.connect.campus.dao;

import com.connect.campus.entities.StudentProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentProgressRepository extends JpaRepository<StudentProgressEntity, String > {
    public StudentProgressEntity findBySubjectId(String id);
}
