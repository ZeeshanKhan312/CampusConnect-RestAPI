package com.connect.campus.services;

import com.connect.campus.dao.*;
import com.connect.campus.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServices {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherScheduleRepository teacherScheduleRepository;
    @Autowired
    ExamScheduleRepository examScheduleRepository;
    @Autowired
    NotificationRepository notificationRepository;

    //This method is checking whether admin exists or not/ whether password is correct or not
    public boolean adminLogin(String adminId, String password){
        AdminEntity admin= null;
        admin=adminRepository.findByAdminIdAndPassword(adminId, password);
        if(admin!=null)
            return true;
        else
            return false;
    }

    public void addAdmin(AdminEntity admin){
        adminRepository.save(admin);
    }

    public void addBatch(BatchEntity batch){
        batch.setBatchId(batch.getCourseName()+batch.getCourseYear());
        batchRepository.save(batch);
    }

    public List<BatchEntity> getBatches(){
        List<BatchEntity> batches= new ArrayList<>();
        batches= batchRepository.findAll();
        return batches;
    }

    public BatchEntity searchBatch(String id){
        BatchEntity batch= new BatchEntity();
        batch=batchRepository.findByBatchId(id);
        return batch;
    }

    public void updateBatchSchedule(String batchId, ScheduleEntity schedule){
        String day=schedule.getDay();
        ScheduleEntity schedule1=scheduleRepository.findByBatchIdAndDay(batchId,day);
        schedule.setScheduleId(schedule1.getScheduleId());
        scheduleRepository.save(schedule);
    }

    public void addStudent(String batchId,List<StudentEntity> students){
        BatchEntity batch=batchRepository.findByBatchId(batchId);
        List<StudentEntity> studentList=batch.getStudents();
        studentList.addAll(students);
        batchRepository.save(batch);
    }

    public List<StudentEntity> searchStudent(String name){
        List<StudentEntity> students = studentRepository.findByStudentName(name);
        return students;
    }

    public void removeStudent(int id){
        studentRepository.deleteById(id);
    }

    public void updateFeesStatus(int id,String transactionId){
        StudentEntity student= studentRepository.findByStudentId(id); //fetching student object by its id
        student.setFeesPaid(true);  //updating fees status in student obj (local not in table)
        student.setTransactionId(transactionId);
        studentRepository.save(student); //updating in table
    }

    public void addExamSchedule(String batchId,List<ExamScheduleEntity> examSchedule){
        BatchEntity batch=batchRepository.findByBatchId(batchId);
        batch.setExamSchedules(examSchedule);
        batchRepository.save(batch);
    }

    public List<ExamScheduleEntity> fetchExamSchedule(String batchId){
        List<ExamScheduleEntity> schedule = new ArrayList <> ();
        schedule = examScheduleRepository.findByExamSchedule(batchId);
        return schedule;
    }

    public void updateBatchSemester(String batchId, String currSem){
        BatchEntity batch= batchRepository.findByBatchId(batchId);
        batch.setCurrentSemester(currSem);
        List<StudentEntity> students=batch.getStudents();
        for(StudentEntity student: students){
            student.setFeesPaid(false);
            student.setTransactionId(null);
        }
        //need to check whether batch.students need to clear first or is it being handled by the setter method
        batch.setStudents(students);
        batchRepository.save(batch);
    }

    public void addTeacher(TeacherEntity teacher){
        teacherRepository.save(teacher);
    }

    public List<TeacherEntity> getAllTeacher(){
        List<TeacherEntity> allTeachers = new ArrayList<>();
        allTeachers=teacherRepository.findAll();
        return allTeachers;
    }

    public void removeTeacher(int teacherId){
        teacherRepository.deleteById(teacherId);
    }

    public List<TeacherEntity> searchTeacher(String name){
        List<TeacherEntity> teachers =teacherRepository.findByTeacherName(name);
        return teachers;
    }

    public void updateTeacherSchedule(int teacherId,TeacherScheduleEntity teacherSchedule){
        String day=teacherSchedule.getDay();
        TeacherScheduleEntity teacherSchedule1=teacherScheduleRepository.findByBatchIdAndDay(teacherId,day);
        teacherSchedule.setScheduleId(teacherSchedule1.getScheduleId());
        teacherScheduleRepository.save(teacherSchedule);
    }

    public void updateTeacherSalary(int teacherId, String salary){
        TeacherEntity teacher=teacherRepository.findByTeacherId(teacherId);
        teacher.setSalary(salary);
        teacherRepository.save(teacher);
    }

    public List<SubjectEntity> allSubjects(){
        List<SubjectEntity> subjectList= new ArrayList<>();
        subjectList=subjectRepository.findAll();
        return subjectList;
    }

    public void addNotification(NotificationEntity notification){
        notificationRepository.save(notification);
    }

    public NotificationEntity searchNotification(String title){
        NotificationEntity notification;
        notification= notificationRepository.findByNotificationTitle(title);
        return notification;
    }

    public List<NotificationEntity> showAllNotifications(){
        List<NotificationEntity> notifications = new ArrayList<>();
        notifications= notificationRepository.findAll();
        return notifications;
    }

}