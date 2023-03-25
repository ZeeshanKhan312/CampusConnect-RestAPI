package com.connect.campus.entities;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "student_progress_table")
@Data
public class StudentProgressEntity {
    @Id
    String subjectId;  //studentId+subjectName
    @NonNull
    int totalAttendance;
    @NonNull
    int totalClasses;
    float attendancePercentage;
    String marks;
    float percentage;

    public StudentProgressEntity(){
        subjectId=null;
        totalAttendance=0;
        totalClasses=0;
    }
}
