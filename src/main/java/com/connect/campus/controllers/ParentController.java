package com.connect.campus.controllers;

import com.connect.campus.entities.*;
import com.connect.campus.services.ParentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
public class ParentController {
    @Autowired
    ParentServices parentServices;

    @PostMapping("/parent_signup")
    public void parentSignUp(@RequestParam int studentId, @RequestBody ParentEntity parent){
         parentServices.addParent(parent,studentId);
    }
    @GetMapping("/parent_login")
    public ParentEntity parentLogin(@RequestParam int parentId, @RequestParam String password){
        return parentServices.login(parentId, password);
    }


    @PutMapping("/change_password")
    public void passwordChange(@RequestParam int parentId, @RequestParam String newPassword){
        parentServices.passwordChange(parentId, newPassword);
    }

    @GetMapping("/notification")
    public List<NotificationEntity> getNotification(){
        return parentServices.getNotification();
    }

    @GetMapping("/search_notification")
    public List<NotificationEntity> searchNotification(@RequestParam String title){
        return parentServices.searchNotification(title);
    }

    @GetMapping("/class_schedules")
    public List<ScheduleEntity>  classSchedule(@RequestParam int studentId){
        return parentServices.classSchedule(studentId);
    }

    @GetMapping("/view_subject_attendance")
    public List<AttendanceEntity> viewSubjectAttendance(@RequestParam int studentId, @RequestParam int subjectId){
        return parentServices.viewSubjectAttendance(studentId, subjectId);
    }

//    @GetMapping("/student_progress")
//    public List<StudentProgressEntity> viewStudentProgress(@RequestParam int studentId, @RequestParam String semester){
//        return parentServices.viewStudentProgress(studentId, semester);
//    }
//
//    @GetMapping("/fetch_exam_schedule")
//    public List<ExamScheduleEntity> fetchingExamSchedule(@RequestParam int studentId){
//        return parentServices.fetchExamSchedule(studentId);
//    }
}
