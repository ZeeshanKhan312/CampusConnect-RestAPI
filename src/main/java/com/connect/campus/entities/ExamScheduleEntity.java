package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@Table(name = "exam_schedule")
@NoArgsConstructor
public class ExamScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_id_generator")
    @SequenceGenerator(name = "exam_id_generator",initialValue =1000,allocationSize = 10,sequenceName = "exam_table_sequence")
    String examId;
    @NonNull
    String date;

    @NonNull
    String subject;

    @NonNull
    String time;

    @NonNull
    String roomAllotted;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    BatchEntity batch;


}