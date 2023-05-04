package com.connect.campus.services;

import com.connect.campus.dao.StudentRepository;
import com.connect.campus.entities.StudentEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@EnableAsync
public class SchedulerServices {

//    TASKS
//    send email for fees related/month
//    send email for attendance shortage/month
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    JavaMailSender mailSender;
//    @Async
//    @Scheduled(fixedRate = 2000)
//    public void scheduleTesting() throws InterruptedException{
//        LocalDateTime time= LocalDateTime.now();
//        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        String formattedTime=time.format(formatter);
//        System.out.println("Scheduler Time: "+ formattedTime);
//        Thread.sleep(5000);
//    }

//    @Async
//    @Scheduled(initialDelay = 3,fixedDelay = 2,timeUnit = TimeUnit.SECONDS)
//    public void testingSchedule(){
//        log.info("INSIDE TESTING Schedule");
//    }

//    @Scheduled(fixedRateString = "PT12S")
//    public void test(){
//        log.info("FixedRate String");
//    }

//    @Scheduled(cron = "3 */1 * 4 * *")
//    public void cron(){
//        log.info("Testing CRON-- method");
//    }

//    @Scheduled(cron = "${cron.expression.value}")
//    public void cronThroughApplProp(){
//        log.info("Getting CRON values from application properties");
//    }

//    @Scheduled(cron = "@hourly")
//    public void inBuiltMacros(){
//        log.info("Using InBuilt macros in CRON");
//    }


//    @Async
//    @Scheduled(cron = "0 59 23 28 * *" )
    @Scheduled(cron = "0 */2 * * * *")
    public void feesUnpaidMail(){
        List<StudentEntity> students=studentRepository.findUnpaidStudents(0);
        List<String> parentEmails= new ArrayList<>();
        List<String> studentEmails= new ArrayList<>();

        for(StudentEntity student:students){
            parentEmails.add(student.getParentEmail());
            studentEmails.add(student.getStudentEmail());
        }

        String subject="Reminder: Payment of College Fees";
        String body="Dear Parent / Student,\n" +
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

        //Sending mail to parent
        for(String parentMail:parentEmails){
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(parentMail);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }

        //Sending mail to student
        for(String studentEmail: studentEmails){
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("campusconnectJH@gmail.com");
            mailMessage.setTo(studentEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            mailSender.send(mailMessage);
        }
    }

}
