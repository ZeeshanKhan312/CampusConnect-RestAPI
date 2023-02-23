package com.connect.campus.dao;

import com.connect.campus.entities.TeacherScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherScheduleRepository extends JpaRepository<TeacherScheduleEntity, Integer> {
}
