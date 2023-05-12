package com.connect.campus.services;

import com.connect.campus.dao.*;
import com.connect.campus.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                System.out.println("absent"+ student.getParentEmail());
                parentEmails.add(student.getParentEmail());
            }
            String mailBody="This is to inform you that your child has not attended class of"+ subjectId +"on"+ attendances.get(0).getAttendance().getDate();

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
        for(String email: parentEmails){
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            System.out.println("sending mail "+ email);
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(email);
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
            slot.setSlot("Slot 1 ");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot2().equals("") && batchSchedules.getSlot2().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 2 ");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
        }
        if(teacherSchedule.getSlot3().equals("") && batchSchedules.getSlot3().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 3 ");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
            System.out.println(availableSlots);
        }
        if(teacherSchedule.getSlot4().equals("") && batchSchedules.getSlot4().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 4 ");
            slot.setTeacherId(teacherId);
            slot.setBatchId(batchId);
            slot.setDay(day);
            availableSlots.add(slot);
            System.out.println(availableSlots);
        }
        if(teacherSchedule.getSlot5().equals("") && batchSchedules.getSlot5().equals("")){
            AvailableSlot slot=new AvailableSlot();
            slot.setSlot("Slot 5 ");
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

        //SENDING EMAIL'S TASK
        List<String> studentsEmail= new ArrayList<>();
        for(StudentEntity student: students){
            studentsEmail.add(student.getStudentEmail());
        }
        String emailSubject="Extra Class Mail";
        String emailBody="This is to notify the students of "+ bookSlot.getBatchId() + " that they have an extra class on "+bookSlot.getDay()+" at " +bookSlot.getSlot() ;

        sendExtraClassMail(studentsEmail,emailSubject,emailBody);

        //SENDING NOTICE TO NOTICE_TABLE
        NotificationEntity notice= new NotificationEntity();
        Date date= new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MMM.yyyy");
        notice.setDate(dateFormat.format(date));
        notice.setNotificationTitle("Extra Class Notice");
        notice.setNotificationMessage("This is to notify the students of "+ bookSlot.getBatchId() + " that they have an extra class on "+bookSlot.getDay()+" at " +bookSlot.getSlot());
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

    public List<NotificationEntity> searchNotice(String title) {
        List<NotificationEntity>notices =notificationRepository.findByNotificationTitle(title);
        return notices;
    }

    public List<NotificationEntity> allNotices(){
        List<NotificationEntity> notices = notificationRepository.findAll();
        return notices;
    }

}
