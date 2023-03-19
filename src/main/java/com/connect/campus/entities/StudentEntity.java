package com.connect.campus.entities;

import javax.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "student_table")
@Data
@DynamicUpdate
public class StudentEntity {
    @Id
    @Column(name ="student_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_id_generator")
    @SequenceGenerator(name = "student_id_generator",initialValue = 2000,allocationSize = 1,sequenceName = "student_table_sequence")
    int studentId;

    @NonNull
    String password;

    @NonNull
    String studentName;

    @NonNull
    String studentEmail;

    @NonNull
    String parentEmail;

    @NonNull
    Boolean feesPaid;

    String transactionId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id",referencedColumnName = "student_id")
    List<AttendanceEntity> attendances;

    public StudentEntity() {
        feesPaid = true;
        password="12345";
    }
}
