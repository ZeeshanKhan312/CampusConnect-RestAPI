package com.connect.campus.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "Teacher_table")
@Data
@DynamicUpdate
@NoArgsConstructor
@Transactional
public class TeacherEntity {
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_generator")
    @SequenceGenerator(name = "teacher_id_generator", initialValue = 500, allocationSize = 2, sequenceName= "teacher_id_table")
    int teacherId;

    @NonNull
    String teacherPassword;

    @NonNull
    String teacherName;

    @NonNull
    String salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    SubjectEntity subject;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id",referencedColumnName = "teacher_id")
    List<TeacherScheduleEntity> teacherSchedule;

}
