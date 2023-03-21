package com.connect.campus.services;

import com.connect.campus.dao.*;
import com.connect.campus.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServices {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TeacherScheduleRepository teacherScheduleRepository;
    @Autowired
    ScheduleRepository batchScheduleRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    public TeacherEntity teacherLogin(int teacherId, String password) {
        TeacherEntity teacher=teacherRepository.findByTeacherIdAndTeacherPassword(teacherId,password);
        return  teacher;
    }

    public Boolean passwordChange(int teacherId, String oldPassword, String newPassword){
        TeacherEntity teacher=null;
        teacher=teacherRepository.findByTeacherIdAndTeacherPassword(teacherId,oldPassword);
        if(teacher!=null){
            teacher.setTeacherPassword(newPassword);
            teacherRepository.save(teacher);
            return true;
        }
        else
            return false;
    }

    public List<TeacherScheduleEntity> getTeacherSchedule(int teacherId) {
        List<TeacherScheduleEntity> schedules=new ArrayList<>();
        schedules.addAll(teacherScheduleRepository.findByTeacherId(teacherId));
        return schedules;

    }

    public List<AvailableSlot> checkEmptySlot(int teacherId, String batchId, String day) {
        ScheduleEntity batchSchedules = batchScheduleRepository.findByBatchIdAndDay(batchId,day);
        TeacherScheduleEntity teacherSchedule= teacherScheduleRepository.findByTeacherIdAndDay(teacherId,day);

        List<AvailableSlot> availableSlots= new ArrayList<>();
        if(teacherSchedule.getSlot1()==null && batchSchedules.getSlot1()==null){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 1 is available");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
       if(teacherSchedule.getSlot2()==null && batchSchedules.getSlot2()==null){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 2 is available");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot3()==null && batchSchedules.getSlot3()==null){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 3 is available");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
            System.out.println(availableSlots);
        }
        if(teacherSchedule.getSlot4()==null && batchSchedules.getSlot4()==null){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 4 is available");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
            System.out.println(availableSlots);
        }
        if(teacherSchedule.getSlot5()==null && batchSchedules.getSlot5()==null){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 5 is available");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        else
            return null;

        return availableSlots;
    }

    public List<StudentEntity> batchStudents(String batchId) {
        List<StudentEntity> students=new ArrayList<>();
        students.addAll(studentRepository.findByBatchId(batchId));
        return students;
    }


    public void markAttendance(AttendanceEntity studentsAttendance, int studentId, int subjectId) {
        StudentEntity student= studentRepository.findByStudentId(studentId);
        List<AttendanceEntity> attendances=student.getAttendances();
        studentsAttendance.setAttendanceId(subjectId+studentId+studentsAttendance.getDate());
        attendances.add(studentsAttendance);
        student.setAttendances(attendances);
        studentRepository.save(student);

        SubjectEntity subject= subjectRepository.findBySubjectId(subjectId);
        List<AttendanceEntity> subjectAttendance=subject.getAttendances();
        subjectAttendance.add(studentsAttendance);
        subject.setAttendances(subjectAttendance);
        subjectRepository.save(subject);
    }
}
