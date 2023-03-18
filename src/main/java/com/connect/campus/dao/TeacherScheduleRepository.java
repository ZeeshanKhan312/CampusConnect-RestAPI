package com.connect.campus.dao;

import com.connect.campus.entities.TeacherScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherScheduleRepository extends JpaRepository<TeacherScheduleEntity, Integer> {
    @Query(value = "SELECT * FROM teacher_schedule WHERE teacher_id=:teacherId AND day=:day", nativeQuery = true)
    public TeacherScheduleEntity findByTeacherIdAndDay(@Param("teacherId")int teacher_id, @Param("day") String day);

    @Query(value = "SELECT * FROM teacher_schedule WHERE teacher_id=:teacherId",nativeQuery = true)
    public List<TeacherScheduleEntity> findByTeacherId(@Param("teacherId")int teacher_id);
}
