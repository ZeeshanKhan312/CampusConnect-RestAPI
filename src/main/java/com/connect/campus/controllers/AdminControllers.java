package com.connect.campus.controllers;

import com.connect.campus.entities.*;
import com.connect.campus.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class AdminControllers {
    @Autowired
    AdminServices adminServices;

    @PostMapping("/add_admin")
    public void add_admin(@RequestBody AdminEntity admin){
        adminServices.addAdmin(admin);
    }

    @GetMapping("/admin_login")
    public ResponseEntity<String> adminLogin(@RequestParam String adminId, @RequestParam String password){
        boolean login;
        login=adminServices.adminLogin(adminId, password);
        if(login)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login SuccessFull.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login Denied!");
    }
    @PostMapping("/add_batch")
    public void addBatch(@RequestBody BatchEntity batch){
        adminServices.addBatch(batch);
    }

    @GetMapping("/get_batches")
    public List<BatchEntity> getBatches(){
        return adminServices.getBatches();
    }

    @GetMapping("/search_batch")
    public BatchEntity searchBatch(@RequestParam String batchId){
        return adminServices.searchBatch(batchId);
    }

    @PutMapping("/update_batch_schedule")
    public void updateBatchSchedule(@RequestParam String batchId, @RequestBody ScheduleEntity schedule){
        adminServices.updateBatchSchedule(batchId,schedule);
    }

    @PostMapping("/add_student")
    public void adding_student(@RequestParam String batchId,@RequestBody List<StudentEntity> students){
        adminServices.addStudent(batchId,students);
    }

    @GetMapping("/search_student")
    public List<StudentEntity> findingStudent(@RequestParam String name){
        return adminServices.searchStudent(name);
    }

    @DeleteMapping("/remove_student")
    public void removeStudent(@RequestParam int studentId){
        adminServices.removeStudent(studentId);
    }

    @PutMapping("/update_fees_status")
    public void updateFees(@RequestParam int studentId,@RequestParam String transactionId){
        adminServices.updateFeesStatus(studentId,transactionId);
    }

    @PostMapping("/add_exam_schedule")
    public void addingExamSchedule(@RequestParam String batchId,@RequestBody List<ExamScheduleEntity> examSchedule){
        adminServices.addExamSchedule(batchId,examSchedule);
    }

    @GetMapping("/fetch_exam_schedule")
    public List<ExamScheduleEntity> fetchingExamSchedule(@RequestParam String batchId){
        return adminServices.fetchExamSchedule(batchId);
    }

    @PutMapping("/update_batch_semester")
    public void updateSemester(@RequestParam String batchId, @RequestParam String currSem){
        adminServices.updateBatchSemester(batchId, currSem);
    }

    @PostMapping("/add_teacher")
    public void addNewTeacher(@RequestBody TeacherEntity teacher){
        adminServices.addTeacher(teacher);
    }

    @GetMapping("/get_teacher")
    public List<TeacherEntity> getTeacher(){
        return adminServices.getAllTeacher();
    }

    @DeleteMapping("/remove_teacher")
    public void removing_teacher(@RequestParam int teacherId){
        adminServices.removeTeacher(teacherId);
    }

    @GetMapping("/search_teacher")
    public List<TeacherEntity> searchTeacher(@RequestParam String name){
     return adminServices.searchTeacher(name);
    }

    @PutMapping("/update_teacher_schedule")
    public void updateTeacherSchedule(@RequestParam int  teacherId, @RequestBody TeacherScheduleEntity teacherSchedule){
        adminServices.updateTeacherSchedule(teacherId,teacherSchedule);
    }

    @PutMapping("/update_salary")
    public void updating_teacher_salary(@RequestParam int teacherId,@RequestParam String salary){
        adminServices.updateTeacherSalary(teacherId, salary);
    }

    @GetMapping("/fetch_subject_list")
    public List<SubjectEntity> subjectList(){
        return adminServices.allSubjects();
    }

    @PostMapping("/add_notification")
    public void addNotification(@RequestBody NotificationEntity notification){
        adminServices.addNotification(notification);
    }

    @GetMapping("/search_notification")
    public NotificationEntity searchNotification(@RequestParam String title){
        return adminServices.searchNotification(title);
    }

    @GetMapping("/all_notifications")
    public List<NotificationEntity> allNotifications(){
        return adminServices.showAllNotifications();
    }

}