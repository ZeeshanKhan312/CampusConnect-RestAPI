package com.connect.campus.services;

import com.connect.campus.dao.TeacherRepository;
import com.connect.campus.entities.TeacherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServices {
    @Autowired
    TeacherRepository teacherRepository;

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
}
