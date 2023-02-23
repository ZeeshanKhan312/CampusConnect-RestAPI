package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "exam_schedule")
@Data
@DynamicUpdate
@NoArgsConstructor
public class ExamScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_id_generator")
    @SequenceGenerator(name = "exam_id_generator", initialValue = 8000, allocationSize = 7, sequenceName= "exam_id_table")
    String examId;

    @NonNull
    String date;

    @NonNull
    String subject;

    @NonNull
    String time;

    @NonNull
    String roomAllotted;

}
