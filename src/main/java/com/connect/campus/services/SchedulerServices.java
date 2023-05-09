package com.connect.campus.services;

import com.connect.campus.dao.StudentProgressRepository;
import com.connect.campus.dao.StudentRepository;
import com.connect.campus.dao.SubjectRepository;
import com.connect.campus.entities.StudentEntity;
import com.connect.campus.entities.SubjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@EnableAsync
public class SchedulerServices {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentProgressRepository studentProgressRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    JavaMailSender mailSender;

    @Scheduled(cron = "0 0 */22 * * *")
    @Async
//    @Scheduled(cron = "0 59 23 28 * *" )
    public void feesUnpaidMail(){
        List<StudentEntity> students=studentRepository.findUnpaidStudents(0);

        String subject, body;
        //SENDING MAIL TO PARENTS
        for(StudentEntity student:students){
            subject="Reminder: Payment of College Fees";
            body="Dear Parent of "+student.getStudentName()+",\n" +
                    "\n" +
                    "I hope this email finds you well. I am writing to remind you that the payment of your child college fees is due. We have not received your payment yet, and it is important that you make the payment as soon as possible to ensure that your child enrollment for the upcoming semester is confirmed.\n" +
                    "\n" +
                    "Please note that failure to make payment on time may result in late payment fees or even cancellation of your child enrollment. We want to ensure that your child have a smooth and successful academic journey with us, and we encourage you to prioritize the payment of your fees.\n" +
                    "\n" +
                    "If you have any questions or concerns regarding the fees, please do not hesitate to contact our finance department. They will be happy to assist you with any queries you may have.\n" +
                    "\n" +
                    "Thank you for your attention to this matter.\n" +
                    "\n" +
                    "Sincerely,\n" +
                    "\n" +
                    "Jamia Hamdard";
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(student.getParentEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }

        //SENDING MAIL TO STUDENT
        for(StudentEntity student: students){
            subject="Reminder: Payment of College Fees";
            body="Dear "+student.getStudentName()+",\n" +
                    "\n" +
                    "I hope this email finds you well. I am writing to remind you that the payment of your college fees is due. We have not received your payment yet, and it is important that you make the payment as soon as possible to ensure that your enrollment for the upcoming semester is confirmed.\n" +
                    "\n" +
                    "Please note that failure to make payment on time may result in late payment fees or even cancellation of your enrollment. We want to ensure that you have a smooth and successful academic journey with us, and we encourage you to prioritize the payment of your fees.\n" +
                    "\n" +
                    "If you have any questions or concerns regarding your fees, please do not hesitate to contact our finance department. They will be happy to assist you with any queries you may have.\n" +
                    "\n" +
                    "Thank you for your attention to this matter.\n" +
                    "\n" +
                    "Sincerely,\n" +
                    "\n" +
                    "Jamia Hamdard";
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(student.getStudentEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }

    }

    @Scheduled(cron = "0 0 */22 * * *")
    @Async
//    @Scheduled(cron = "0 59 23 28 * *" )
    public void attendanceShortageMail(){
        List<Object[]> list= studentProgressRepository.getShortAttendanceList(60);

        for(Object[] object: list){
            int sId=(Integer) object[0];
            StudentEntity student= studentRepository.findByStudentId(sId);
            Integer subId=(Integer) object[1];
            SubjectEntity subject= subjectRepository.findBySubjectId(subId);

            String mailSubject="Concerns Regarding Your Attendance";
            String body= "Dear Parent of "+student.getStudentName()+",\n" +
                    "\n" +
                    "I am writing to express my concern about your child attendance in "+subId+"-"+subject.getSubjectName()+ ". According to our records, your child have missed several classes without providing any explanation or prior notice.\n" +
                    "\n" +
                    "And this is worrying. While I understand that unforeseen circumstances may arise, repeated absences can severely affect your child progress and, ultimately, their grades/performance.\n"+
                    "Therefore, I urge you to make sure that your child take attendance seriously and make effort to attend lectures regularly. If your child is facing any challenges that is hindering their attendance, please feel free to reach out to us or the subject teacher for assistance. We are always willing to help in any way that we can." +
                    "Please let us know if there is anything we can do to support you and your child studies.\n" +
                    "\n" +
                    "Sincerely,\n" +
                    "\n" +
                    "Jamia Hamdard";

            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(student.getParentEmail());
            mailMessage.setSubject(mailSubject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);

        }

        for(Object[] object: list){
            int sId=(Integer) object[0];
            StudentEntity student= studentRepository.findByStudentId(sId);
            Integer subId=(Integer) object[1];
            SubjectEntity subject= subjectRepository.findBySubjectId(subId);

            String mailSubject="Concerns Regarding Your Attendance";
            String body= "Dear "+student.getStudentName()+",\n" +
                    "\n" +
                    "I am writing to express my concern about your attendance in "+subId+"-"+subject.getSubjectName()+ ". According to our records, you have missed several classes without providing any explanation or prior notice.\n" +
                    "\n" +
                    "And this is worrying. While I understand that unforeseen circumstances may arise, repeated absences can severely affect your progress and, ultimately, your grades/performance.\n"+
                    "Therefore, I urge you to make sure that you take attendance seriously and make effort to attend lectures regularly. If you are facing any challenges that are hindering your attendance, please feel free to reach out to us or the subject teacher for assistance. We are always willing to help in any way that we can." +
                    "Please let us know if there is anything we can do to support you and your studies.\n" +
                    "\n" +
                    "Sincerely,\n" +
                    "\n" +
                    "Jamia Hamdard";

            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(student.getStudentEmail());
            mailMessage.setSubject(mailSubject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);

        }
    }

}
