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

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    StudentProgressRepository studentProgressRepository;

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




    public void sendNotice(NotificationEntity notice){
        notificationRepository.save(notice);
    }


    public List<NotificationEntity> searchNotice(String title) {
        List<NotificationEntity>notices =notificationRepository.findByNotificationTitle(title);
        return notices;
    }

    public List<NotificationEntity> allNotices(){
        List<NotificationEntity> notices = notificationRepository.findAll();
        return notices;
    }

    public void sendExtraClassEmail(String slot, String batchId, String teacherName){
        BatchEntity batch= batchRepository.findByBatchId(batchId);
        List<StudentEntity>students=batch.getStudents();

        List<String> studentsEmail= new ArrayList<>();
        for(StudentEntity student: students){
            studentsEmail.add(student.getStudentEmail());
        }
        System.out.println(studentsEmail);

        NotificationEntity notice= new NotificationEntity();
        notice.setNotificationTitle("Extra Class Notice");
        notice.setNotificationMessage("This is to notify the students of "+ batchId + "that they have an extra class on " +slot);
        notice.setAuthor(teacherName);

        sendNotice(notice);


    }

    public void markAttendance(int subjectId, List<StudentEntity> students, List<AttendanceEntity> attendances) {
        List<String> parentEmails= new ArrayList<>();
        for(int i=0; i<students.size(); i++){
            StudentEntity student =students.get(i);
            AttendanceEntity attendance= attendances.get(i);
            attendance.setAttendanceId(student.getStudentId()+subjectId+attendance.getDate());

            //adding attendance records in student table
            List<AttendanceEntity> studentAttendances=student.getAttendances();
            studentAttendances.add(attendance);
            studentRepository.save(student);

            //adding attendance record in subject table
            SubjectEntity subject= subjectRepository.findBySubjectId(subjectId);
            List<AttendanceEntity> subjectAttendance= subject.getAttendances();
            subjectAttendance.add(attendance);
            subjectRepository.save(subject);

            //storing parents' email of those who have not attended the class
            if(attendance.getPresent()=="false"){
                parentEmails.add(student.getParentEmail());
            }

            //updating student progress (student progress=subject-student relation)
            String id= student.getStudentId() + subject.getSubjectName();
            StudentProgressEntity studentProgress= studentProgressRepository.findBySubjectId(id);
            int totalAttendance=0;
            int totalClasses=0;
            float attendancePercentage;
            if(attendance.getPresent()=="true"){
                totalAttendance=studentProgress.getTotalAttendance()+1;
                studentProgress.setTotalAttendance(totalAttendance);
            }
            totalClasses=studentProgress.getTotalClasses()+1;
            studentProgress.setTotalClasses(totalClasses);
            attendancePercentage=(totalAttendance/totalClasses)*100;
            studentProgress.setAttendancePercentage(attendancePercentage);
            studentRepository.save(student);
        }
    }

}
