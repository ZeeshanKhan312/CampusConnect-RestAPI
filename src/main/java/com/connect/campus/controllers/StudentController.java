package com.connect.campus.controllers;

import com.connect.campus.entities.*;
import com.connect.campus.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentServices studentServices;
    @GetMapping("/student_login")
    public StudentEntity studentLogin(@RequestParam int studentId,@RequestParam String password){
        return studentServices.studentLogin(studentId, password);
    }

    @PutMapping("/change_password")
    public void passwordChange(@RequestParam int studentId, @RequestParam String newPassword){
        studentServices.passwordChange(studentId, newPassword);
    }

    @GetMapping("/notification")
    public List<NotificationEntity> getNotification(){
        return studentServices.getNotification();
    }

    @GetMapping("/search_notification")
    public List<NotificationEntity> searchNotification(@RequestParam String title){
        return studentServices.searchNotification(title);
    }

    @GetMapping("/class_schedules")
    public List<ScheduleEntity>  classSchedule(@RequestParam int studentId){
        return studentServices.classSchedule(studentId);
    }

    @GetMapping("/view_subject_attendance")
    public List<AttendanceEntity> viewSubjectAttendance(@RequestParam int studentId, @RequestParam int subjectId){
        return studentServices.viewSubjectAttendance(studentId, subjectId);
    }

    @GetMapping("/student_progress")
    public List<StudentProgressEntity> viewStudentProgress(@RequestParam int studentId){
        return studentServices.viewStudentProgress(studentId);
    }

    @GetMapping("/fetch_exam_schedule")
    public List<ExamScheduleEntity> fetchingExamSchedule(@RequestParam int studentId){
        return studentServices.fetchExamSchedule(studentId);
    }

}
