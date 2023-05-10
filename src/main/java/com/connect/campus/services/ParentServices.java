package com.connect.campus.services;

import com.connect.campus.dao.*;
import com.connect.campus.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParentServices {
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentProgressRepository studentProgressRepository;
    @Autowired
    ExamScheduleRepository examScheduleRepository;

    public void addParent(ParentEntity parent, int studentId) {
        StudentEntity student= studentRepository.findByStudentId(studentId);
        if(student.getParent()!=null)
            throw new ArithmeticException();
        else{
            student.setParent(parent);
            studentRepository.save(student);

            int parentId=student.getParent().getParentId();
            String subject, body;
            subject="Your Account is Successfully Created";
            body="Dear User\n" +
                    "Your Parent Account for student "+studentId+ " has been successfully created. You can find your User Id below\n " +
                    "User Id: " + parentId+"\n for any other query feel free to contact us.";

            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(parent.getParentEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }
    }

    public ParentEntity login(int parentId, String password){
        ParentEntity parent= parentRepository.findParent(parentId,password);
        if(parent==null)
            throw  new ArithmeticException();
        return parent;
    }

    public void passwordChange(int parentId, String newPassword) {
        ParentEntity parent= parentRepository.findByParentId(parentId);
        if(parent==null)
            throw new ArithmeticException();
        else {
            parent.setPassword(newPassword);
            parentRepository.save(parent);
        }
    }

    public List<NotificationEntity> getNotification() {
        List<NotificationEntity> notifications= new ArrayList<>();
        notifications.addAll(notificationRepository.findAll());
        return notifications;
    }

    public List<NotificationEntity> searchNotification(String title) {
        List<NotificationEntity> notifications= notificationRepository.findByNotificationTitle(title);
        return notifications;
    }

    public List<ScheduleEntity> classSchedule(int studentId) {
        String batchId= studentRepository.findBatchIdByStudentId(studentId);
        BatchEntity batch=batchRepository.findByBatchId(batchId);
        return  batch.getSchedules();
    }

    public List<AttendanceEntity> viewSubjectAttendance(int studentId, int subjectId) {
        List<AttendanceEntity> attendanceList= attendanceRepository.findByStudentIdAndSubjectId(studentId, subjectId);
        if(attendanceList.isEmpty())
            throw new NullPointerException();
        return attendanceList;
    }

    public List<StudentProgressEntity> viewStudentProgress(int studentId, String semester) {
        List<StudentProgressEntity> list= studentProgressRepository.findStudentProgress(studentId, semester);
        if(list.isEmpty())
            throw new ArithmeticException();
        return list;
    }

    public List<ExamScheduleEntity> fetchExamSchedule(int studentId){
        String batchId= studentRepository.findBatchIdByStudentId(studentId);
        List<ExamScheduleEntity> schedule = examScheduleRepository.findByBatchId(batchId);
        if(schedule.isEmpty())
            throw new ArithmeticException();
        else
        return schedule;
    }

}
