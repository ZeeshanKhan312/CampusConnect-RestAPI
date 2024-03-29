package com.connect.campus.controllers;

import com.connect.campus.entities.*;
import com.connect.campus.services.TeacherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServices teacherServices;

    @GetMapping("/teacher_login")
    public TeacherEntity teacherLogin(@RequestParam int teacherId, @RequestParam String password){
        return teacherServices.teacherLogin(teacherId,password);
    }

    @PutMapping("/change_teacher_password")
    public  void changePassword(@RequestParam int teacherId, @RequestParam String oldPassword, @RequestParam String newPassword){
        teacherServices.passwordChange(teacherId, oldPassword, newPassword);
    }

    @GetMapping("/teacher_schedule")
    public List<TeacherScheduleEntity> getTeacherSchedule(int teacherId){
        return teacherServices.getTeacherSchedule(teacherId);
    }

    @GetMapping("/batch_students")
    public List<StudentEntity> batchStudents(@RequestParam String batchId){
        return teacherServices.batchStudents(batchId);
    }

    @PostMapping("/mark_attendance")
    public void markAttendance(@RequestParam int subjectId, @RequestParam String batchId, @RequestBody List<MarkAttendance> attendances){
        teacherServices.markAttendance(subjectId, batchId, attendances);
    }

    @GetMapping("/detailed_attendance")
    public List<AttendanceEntity> detailedAttendance(@RequestParam int studentId, @RequestParam  int subjectId){
        return teacherServices.detailedAttendance(studentId,subjectId);
    }

    @GetMapping("view_batch_attendance")
    public List<StudentProgressEntity> viewBatchAttendance(@RequestParam String batchId,@RequestParam int subjectId){
        return teacherServices.viewBatchAttendance(batchId, subjectId);
    }

    @PostMapping("/upload_marks")
    public void uploadMarks(@RequestBody List<StudentProgressEntity> studentsProgress){
        teacherServices.uploadMarks(studentsProgress);
    }

    @GetMapping("/empty_slot")
    public List<AvailableSlot> emptySlot(@RequestParam int teacherId, @RequestParam String batchId, @RequestParam String day){
        return teacherServices.checkEmptySlot(teacherId, batchId, day);
    }

    @PostMapping("/book_extra_class")
    public void bookExtraClass(@RequestBody AvailableSlot bookSlot){
        teacherServices.bookExtraClass(bookSlot);
    }

    @PostMapping("/send_notice")
    public void sendNotice(@RequestBody NotificationEntity notice){
        teacherServices.sendNotice(notice);
    }

    @GetMapping("/search_notice")
    public List<NotificationEntity> searchNotice(@RequestParam String title ){
        return teacherServices.searchNotice(title);
    }

    @GetMapping("/show_all_notices")
    public List<NotificationEntity> allNotices(){
        return teacherServices.allNotices();
    }


}
