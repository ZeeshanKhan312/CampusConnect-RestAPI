package com.connect.campus.entities;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "teacher_table")
@Data
@DynamicUpdate
public class TeacherEntity {
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_generator")
    @SequenceGenerator(name = "teacher_id_generator",initialValue =3000,allocationSize = 10,sequenceName = "teacher_table_sequence")
    int teacherId;

    @NonNull
    String teacherPassword;

    @NonNull
    String teacherName;

    String teacherEmail;

    @NonNull
    String salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    SubjectEntity subject;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id",referencedColumnName = "teacher_id")
    List<TeacherScheduleEntity> teacherSchedules;

    public TeacherEntity() {
        teacherPassword="abc123";
    }
}
