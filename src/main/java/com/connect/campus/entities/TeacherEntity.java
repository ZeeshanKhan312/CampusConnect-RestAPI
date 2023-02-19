package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Teacher_table")
@Data
@DynamicUpdate
@NoArgsConstructor
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_generator")
    @SequenceGenerator(name = "teacher_id_generator",initialValue =3000,allocationSize = 10,sequenceName = "teacher_table_sequence")
    int teacherId;

    @NonNull
    String teacherPassword;

    @NonNull
    String teacherName;

    @NonNull
    String salary;

    @OneToOne
    SubjectEntity subject;

}
