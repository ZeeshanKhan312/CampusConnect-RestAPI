package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@Table(name = "student_table")
public class StudentEntity {
    @Id
    @Column(name ="studentID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_generator")
    @SequenceGenerator(name = "student_id_generator",initialValue =2000,allocationSize = 1,sequenceName = "schedule_table_sequence")
    int studentId;

    @NonNull
    String password;

    @NonNull
    String studentName;

    @NonNull
    String studentEmail;

    @NonNull
    String parentsEmail;

    @NonNull
    Boolean feesPaid;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    BatchEntity batch;

    public StudentEntity() {
        feesPaid = true;
    }
}
