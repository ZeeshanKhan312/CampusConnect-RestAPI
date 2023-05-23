package com.connect.campus.services;

import com.connect.campus.dao.*;
import com.connect.campus.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@EnableAsync
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

    @Autowired
    JavaMailSender mailSender;

    public TeacherEntity teacherLogin(int teacherId, String password) {
        TeacherEntity teacher=teacherRepository.findByTeacherIdAndTeacherPassword(teacherId,password);
        return  teacher;
    }

    public void passwordChange(int teacherId, String oldPassword, String newPassword){
        TeacherEntity teacher=teacherRepository.findByTeacherIdAndTeacherPassword(teacherId,oldPassword);
        if(teacher!=null){
            teacher.setTeacherPassword(newPassword);
            teacherRepository.save(teacher);
        }
    }

    public List<TeacherScheduleEntity> getTeacherSchedule(int teacherId) {
        List<TeacherScheduleEntity> schedules=new ArrayList<>();
        schedules.addAll(teacherScheduleRepository.findByTeacherId(teacherId));
        return schedules;

    }

    public List<StudentEntity> batchStudents(String batchId) {
        List<StudentEntity> students=new ArrayList<>();
        students.addAll(studentRepository.findByBatchId(batchId));
        if(students.isEmpty())
            throw new NullPointerException();
        return students;
    }

    public void markAttendance(int subjectId, String batchId, List<MarkAttendance> attendances) {
        String semester=batchRepository.findByCurrentSemester(batchId);
        List<String> parentEmails= new ArrayList<>();
        for(int i=0; i<attendances.size(); i++){
            StudentEntity student =studentRepository.findByStudentId(attendances.get(i).getStudentId());
            AttendanceEntity attendance= attendances.get(i).getAttendance();
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

            //SENDING EMAIL TASK
            //storing parents' email of those who have not attended the class
            if(attendance.getPresent().equals("false")){
                parentEmails.add(student.getParentEmail());
            }
            String mailBody="This is to inform you that your child "+student.getStudentName()+" has not attended class of "+ subjectId +" on "+ attendances.get(0).getAttendance().getDate();

            sendAbsentMail(parentEmails, mailBody);


            //updating student progress (student progress=subject-student relation)
            String id= student.getStudentId() + subject.getSubjectName();
            StudentProgressEntity studentProgress= studentProgressRepository.findByProgressId(id);
            int totalAttendance=0;
            int totalClasses=0;
            float attendancePercentage;
            if(studentProgress==null){
                studentProgress=new StudentProgressEntity();
                studentProgress.setProgressId(id);
                studentProgress.setBatchId(batchId);
                studentProgress.setSemester(semester);
            }
            else {
                totalAttendance=studentProgress.getTotalAttendance();
                totalClasses=studentProgress.getTotalClasses();
            }

            if(attendance.getPresent().equals("true")){
                totalAttendance++;
                studentProgress.setTotalAttendance(totalAttendance);
            }
            totalClasses++;
            studentProgress.setTotalClasses(totalClasses );
            attendancePercentage=((float)totalAttendance/totalClasses)*100;
            studentProgress.setAttendancePercentage(attendancePercentage);

            List<StudentProgressEntity>studentProgressList=student.getStudentProgress();
            studentProgressList.add(studentProgress);
            student.setStudentProgress(studentProgressList);
            studentRepository.save(student);

            List<StudentProgressEntity> subjectProgress=subject.getStudentProgress();
            subjectProgress.add(studentProgress);
            subject.setStudentProgress(subjectProgress);
            subjectRepository.save(subject);
        }
    }

    public void sendAbsentMail(List<String> parentEmails, String body){
        for(int i=0;i<parentEmails.size();i++){
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(parentEmails.get(i));
            mailMessage.setSubject("Regarding Attendance");
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }
    }

    public List<AttendanceEntity> detailedAttendance(int studentId, int subjectId) {
        List<AttendanceEntity> attendanceList= attendanceRepository.findByStudentIdAndSubjectId(studentId,subjectId);
        if(attendanceList.isEmpty())
            throw new NullPointerException();
        return  attendanceList;
    }

    public List<StudentProgressEntity> viewBatchAttendance(String batchId, int subjectId) {
        List<StudentProgressEntity> batchProgress=studentProgressRepository.findByBatchIdAndSubjectId(batchId,subjectId);
        if(batchProgress.isEmpty())
            throw new NullPointerException();
        return  batchProgress;
    }

    public void uploadMarks(List<StudentProgressEntity> studentsProgress) {
        for(int i=0;i<studentsProgress.size();i++) {
            StudentProgressEntity studentProgress=studentProgressRepository.findByProgressId(studentsProgress.get(i).getProgressId());
            studentProgress.setMarks(studentsProgress.get(i).getMarks());
            studentProgressRepository.save(studentProgress);
        }
    }

    public List<AvailableSlot> checkEmptySlot(int teacherId, String batchId, String day) {
        ScheduleEntity batchSchedules = batchScheduleRepository.findByBatchIdAndDay(batchId,day);
        TeacherScheduleEntity teacherSchedule= teacherScheduleRepository.findByTeacherIdAndDay(teacherId,day);

        List<AvailableSlot> availableSlots= new ArrayList<>();
        if(teacherSchedule.getSlot1().equals("") && batchSchedules.getSlot1().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 1");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot2().equals("") && batchSchedules.getSlot2().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 2");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot3().equals("") && batchSchedules.getSlot3().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 3");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot4().equals("") && batchSchedules.getSlot4().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 4");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot5().equals("") && batchSchedules.getSlot5().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 5");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }

        return availableSlots;
    }

    public void bookExtraClass(AvailableSlot bookSlot){
        BatchEntity batch= batchRepository.findByBatchId(bookSlot.getBatchId());
        TeacherEntity teacher=teacherRepository.findByTeacherId(bookSlot.getTeacherId());
        List<StudentEntity>students=batch.getStudents();
        String text= "This is to notify the students of "+ bookSlot.getBatchId() + " that they have an extra class on coming "+bookSlot.getDay()+" at " +bookSlot.getSlot()+ " of subject " +teacher.getSubject().getSubjectName();

//        ScheduleEntity schedule=batchScheduleRepository.findByBatchIdAndDay(bookSlot.getBatchId(), bookSlot.getDay());
//        TeacherScheduleEntity teacherSchedule= teacherScheduleRepository.findByTeacherIdAndDay(bookSlot.getTeacherId(), bookSlot.getDay());
//        if(bookSlot.getSlot().equals("Slot 1")) {
//            schedule.setSlot1(""+teacher.getSubject().getSubjectId());
//            teacherSchedule.setSlot1(bookSlot.getBatchId());
//        }
//        else if (bookSlot.getSlot().equals("Slot 2")) {
//            schedule.setSlot2(""+teacher.getSubject().getSubjectId());
//            teacherSchedule.setSlot2(bookSlot.getBatchId());
//        }
//        else if (bookSlot.getSlot().equals("Slot 3")) {
//            schedule.setSlot3(""+teacher.getSubject().getSubjectId());
//            teacherSchedule.setSlot3(bookSlot.getBatchId());
//        }
//        else if (bookSlot.getSlot().equals("Slot 4")) {
//            schedule.setSlot4(""+teacher.getSubject().getSubjectId());
//            teacherSchedule.setSlot4(bookSlot.getBatchId());
//        }
//        else if (bookSlot.getSlot().equals("Slot 5")) {
//            schedule.setSlot5(""+teacher.getSubject().getSubjectId());
//            teacherSchedule.setSlot5(bookSlot.getBatchId());
//        }
//
////        batch.setSchedules();
//        batchScheduleRepository.save(schedule);
//        teacherScheduleRepository.save(teacherSchedule);
//
//        changeScheduleBack(bookSlot);

        //SENDING EMAIL'S TASK
        List<String> studentsEmail= new ArrayList<>();
        for(StudentEntity student: students){
            studentsEmail.add(student.getStudentEmail());
        }
        String emailSubject="Extra Class Mail";
        String emailBody=text ;
        sendExtraClassMail(studentsEmail,emailSubject,emailBody);

        //SENDING NOTICE TO NOTICE_TABLE
        NotificationEntity notice= new NotificationEntity();
        Date date= new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MMM.yyyy");
        notice.setDate(""+dateFormat.format(date));
        notice.setNotificationTitle("Extra Class Notice");
        notice.setNotificationMessage(text);
        notice.setAuthor(teacher.getTeacherName());
        sendNotice(notice);

    }

    public void sendExtraClassMail(List<String> studentsEmail,String subject, String body) {
        for(String email:studentsEmail){
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }
    }

    public void sendNotice(NotificationEntity notice){
        notificationRepository.save(notice);
    }

//    @Async
//    @Scheduled(initialDelay = 5, fixedDelay = Long.MAX_VALUE, timeUnit = TimeUnit.MINUTES)
//    public void changeScheduleBack(AvailableSlot slot){
//        //changing the schedule of batchId
////        BatchEntity batch=batchRepository.findByBatchId(slot.getBatchId());
//        ScheduleEntity schedule=batchScheduleRepository.findByBatchIdAndDay(slot.getBatchId(), slot.getDay());
//        TeacherScheduleEntity teacherSchedule= teacherScheduleRepository.findByTeacherIdAndDay(slot.getTeacherId(), slot.getDay());
//        if(slot.getSlot().equals("Slot 1")) {
//            schedule.setSlot1("");
//            teacherSchedule.setSlot1("");
//        }
//        else if (slot.getSlot().equals("Slot 2")) {
//            schedule.setSlot2("");
//            teacherSchedule.setSlot2("");
//        }
//        else if (slot.getSlot().equals("Slot 3")) {
//            schedule.setSlot3("");
//            teacherSchedule.setSlot3("");
//        }
//        else if (slot.getSlot().equals("Slot 4")) {
//            schedule.setSlot4("");
//            teacherSchedule.setSlot4("");
//        }
//        else if (slot.getSlot().equals("Slot 5")) {
//            schedule.setSlot5("");
//            teacherSchedule.setSlot5("");
//        }
//
//        batchScheduleRepository.save(schedule);
//        teacherScheduleRepository.save(teacherSchedule);
//
//    }

    public List<NotificationEntity> searchNotice(String title) {
        List<NotificationEntity>notices =notificationRepository.findByNotificationTitle(title);
        return notices;
    }

    public List<NotificationEntity> allNotices(){
        List<NotificationEntity> notices = notificationRepository.findAll();
        return notices;
    }

}
