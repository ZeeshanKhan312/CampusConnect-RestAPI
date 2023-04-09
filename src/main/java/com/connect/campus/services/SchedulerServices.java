package com.connect.campus.services;

import com.connect.campus.dao.StudentRepository;
import com.connect.campus.entities.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SchedulerServices {
    @Autowired
    StudentRepository studentRepository;
    @Scheduled(fixedRate = 2000)
    public void scheduleTesting() throws InterruptedException{
        LocalDateTime time= LocalDateTime.now();
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime=time.format(formatter);
        System.out.println("Scheduler Time: "+ formattedTime);
        Thread.sleep(5000);
    }

    @Scheduled(initialDelay = 3,fixedDelay = 2,timeUnit = TimeUnit.SECONDS)
    public void testingSchedule(){
        log.info("INSIDE TESTING Schedule");
    }

//    public void feesUnpaidMail(){
//        List<StudentEntity> students=studentRepository.findUnpaidStudents();
//        List<String> parentEmails= new ArrayList<>();
//        List<String> studentEmails= new ArrayList<>();
//
//        for(StudentEntity student:students){
//            parentEmails.add(student.getParentEmail());
//            studentEmails.add(student.getStudentEmail());
//        }
//
//        System.out.println();
//
////        //Sending mail to parent
////        for(String parentMail:parentEmails){
////
////        }
////
////        //Sending mail to student
////        for(String studentEmail: studentEmails){
////
////        }
//    }
}
