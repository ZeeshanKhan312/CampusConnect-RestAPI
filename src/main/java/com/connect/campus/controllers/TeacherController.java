package com.connect.campus.controllers;

import com.connect.campus.entities.AvailableSlot;
import com.connect.campus.entities.TeacherEntity;
import com.connect.campus.entities.TeacherScheduleEntity;
import com.connect.campus.services.TeacherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {
//    LOGIN
//    PASSWORD CHANGE
//    VIEW TEACHER SCHEDULE
//    AVAILABILITY OF SLOTS
//    SEND MAIL FOR EXTRA CLASS
//    SEND NOTICE FOR EXTRA CLASS
//    MARK ATTENDANCE
//    UPLOAD MARKS
//    VIEW ATTENDANCE AND PERCENTAGE
//    VIEW DETAILED ATTENDANCE

    @Autowired
    TeacherServices teacherServices;

    @GetMapping("/teacher_login")
    public TeacherEntity teacherLogin(@RequestParam int teacherId, @RequestParam String password){
        return teacherServices.teacherLogin(teacherId,password);
    }

    @PutMapping("/change_teacher_password")
    public ResponseEntity<String> changePassword(@RequestParam int teacherId, @RequestParam String oldPassword, @RequestParam String newPassword){
        boolean success;
        success=teacherServices.passwordChange(teacherId, oldPassword, newPassword);
        if(success){
             return ResponseEntity.accepted().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/teacher_schedule")
    public List<TeacherScheduleEntity> getTeacherSchedule(int teacherId){
        return teacherServices.getTeacherSchedule(teacherId);
    }

    @GetMapping("/empty_slot")
    public List<AvailableSlot> emptySlot(@RequestParam int teacherId, @RequestParam String batchId, @RequestParam String day){
        return teacherServices.checkEmptySlot(teacherId, batchId, day);
    }
}
