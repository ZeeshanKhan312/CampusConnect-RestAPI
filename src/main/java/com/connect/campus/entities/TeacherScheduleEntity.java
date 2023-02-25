package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TeacherSchedule")
@Data
@NoArgsConstructor
@DynamicUpdate
public class TeacherScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_generator")
    @SequenceGenerator(name = "schedule_id_generator", initialValue = 440, allocationSize = 4, sequenceName= "schedule_id_table")
    int scheduleId;

    @NonNull
    String day;
    String slot1;
    String slot2;
    String slot3;
    String slot4;
    String slot5;
}
