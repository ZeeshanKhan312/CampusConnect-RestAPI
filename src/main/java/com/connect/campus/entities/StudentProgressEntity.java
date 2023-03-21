package com.connect.campus.entities;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "student_subject_table")
@Data
public class StudentProgressEntity {
    @Id
    String Id;
    @NonNull
    int totalAttendance;
    @NonNull
    int totalClasses;
    String marks;
    float percentage;

    public StudentProgressEntity(){
        Id=null;
        totalAttendance=0;
        totalClasses=0;
    }
}
