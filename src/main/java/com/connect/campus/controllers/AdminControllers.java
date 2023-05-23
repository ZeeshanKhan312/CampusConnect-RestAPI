package com.connect.campus.controllers;

import com.connect.campus.entities.*;
import com.connect.campus.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminControllers {
    @Autowired
    AdminServices adminServices;

    @PostMapping("/add_admin")
    public void add_admin(@RequestBody AdminEntity admin){
        adminServices.addAdmin(admin);
    }

    @GetMapping("/admin_login")
    public AdminEntity adminLogin(@RequestParam String adminId, @RequestParam String password){
        return adminServices.adminLogin(adminId, password);
    }
    @PutMapping("/admin_password")
    public void changePassword(@RequestParam String adminId,@RequestParam String oldPassword,@RequestParam String newPassword){
        adminServices.changePassword(adminId, oldPassword, newPassword);
    }
    @PostMapping("/add_batch")
    public void addBatch(@RequestBody BatchEntity batch){
        adminServices.addBatch(batch);
    }

    @DeleteMapping("/delete_batch")
    public void deleteBatch(@RequestParam String batchId){
        adminServices.deleteBatch(batchId);
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
    public void updateSemester(@RequestParam String batchId, @RequestParam String currSem, @RequestBody List<ScheduleEntity> schedules){
        adminServices.updateBatchSemester(batchId, currSem, schedules);
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

    @DeleteMapping("/delete_notice")
    public void deleteNotice(@RequestParam int noticeID){
        adminServices.deleteNotice(noticeID);
    }

    @GetMapping("/search_notification")
    public List<NotificationEntity> searchNotification(@RequestParam String title){
        return adminServices.searchNotification(title);
    }

    @GetMapping("/all_notifications")
    public List<NotificationEntity> allNotifications(){
        return adminServices.showAllNotifications();
    }

}