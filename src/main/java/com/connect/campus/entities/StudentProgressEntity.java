package com.connect.campus.entities;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "student_progress_table")
@Data
public class StudentProgressEntity {
    @Id
    String progressId;  //studentId+subjectName
    @Column(name = "batch_id")
    String batchId;
    @NonNull
    int totalAttendance;
    @NonNull
    int totalClasses;
    float attendancePercentage;
    String marks;
    float percentage;

    public StudentProgressEntity(){
        progressId=null;
        totalAttendance=0;
        totalClasses=0;
    }
}
