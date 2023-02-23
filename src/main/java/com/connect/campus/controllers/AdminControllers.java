package com.connect.campus.controllers;

import com.connect.campus.entities.*;
import com.connect.campus.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class AdminControllers {
    @Autowired
    AdminServices adminServices;

    @PostMapping("/add_admin")
    public void adding_admin(@RequestBody AdminEntity admin){
        adminServices.addAdmin(admin);
    }

    @GetMapping("/admin_login")
    public void check_admin_login(@RequestParam String adminId, @RequestParam String password){
        adminServices.adminLogin(adminId, password);
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
    public BatchEntity searchBatch(@RequestParam String id){
        return adminServices.searchBatch(id);
    }


    @PutMapping("/update_schedule")
    public void updateSchedule(@RequestParam String batchId, @RequestBody ScheduleEntity schedule){
        adminServices.updateSchedule(batchId,schedule);
    }
    @PostMapping("/add_student")
    public void adding_student(@RequestParam String batchId,@RequestBody StudentEntity student){
        adminServices.addStudent(batchId,student);
    }

    @GetMapping("/fetch_batch_student")
    public List<StudentEntity> fetching_batch(@RequestParam String batchId){
        return adminServices.fetchStudentBatch(batchId);
    }

    @GetMapping("/search_student")
    public StudentEntity findingStudent(@RequestParam int sId){
        return adminServices.fetchStudentById(sId);
    }

    @DeleteMapping("/remove_student")
    public void removeStudent(@RequestParam int id){
        adminServices.removeStudent(id);
    }

    @PutMapping("/update_fees_status")
    public void updateFees(@RequestParam int id){
        adminServices.updateFeesStatus(id);
    }

    @PostMapping("/add_exam_schedule")
    public void addingExamSchedule(@RequestBody List<ExamScheduleEntity> examSchedule){
        adminServices.addExamSchedule(examSchedule);
    }

    @GetMapping("/fetch_exam_schedule")
    public List<ExamScheduleEntity> fetchingExamSchedule(@RequestParam String batchId){
        return adminServices.fetchExamSchedule(batchId);
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
