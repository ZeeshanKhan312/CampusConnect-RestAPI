package com.connect.campus.controllers;

import com.connect.campus.entities.NotificationEntity;
import com.connect.campus.entities.StudentEntity;
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
}
