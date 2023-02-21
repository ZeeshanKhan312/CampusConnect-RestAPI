package com.connect.campus.dao;

import com.project.etudiant.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Integer > {
    public ScheduleEntity findByScheduleId(int schedule_id);
}
