package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "subject_table")
@Data
@NoArgsConstructor
@DynamicUpdate
@Entity
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_generator")
    @SequenceGenerator(name = "subject_id_generator",initialValue =500,allocationSize = 10,sequenceName = "subject_table_sequence")
    int subjectId;

    @NonNull
    String subjectName;
}
