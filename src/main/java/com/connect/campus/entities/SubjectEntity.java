package com.connect.campus.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "subject_table")
@Data
@NoArgsConstructor
@DynamicUpdate
@Entity
@Transactional
public class SubjectEntity {
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_generator")
    @SequenceGenerator(name = "subject_id_generator", initialValue = 1000, allocationSize = 2, sequenceName= "subject_id_table")
    int subjectId;

    @NonNull
    String subjectName;

}

