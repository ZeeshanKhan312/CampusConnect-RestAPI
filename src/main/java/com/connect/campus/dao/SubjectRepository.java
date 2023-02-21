package com.connect.campus.dao;

import com.project.etudiant.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {
    public SubjectEntity findBySubjectId(int subjectId);
}
