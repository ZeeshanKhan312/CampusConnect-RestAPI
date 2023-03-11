package com.connect.campus.entities;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ScheduleTable")
@DynamicUpdate
@Data
@NoArgsConstructor
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_generator")
    @SequenceGenerator(name = "schedule_id_generator", initialValue = 230, allocationSize = 2, sequenceName= "schedule_table_sequence")
    int scheduleId;

    @NonNull
    String day;
    String slot1;
    String slot2;
    String slot3;
    String slot4;
    String slot5;

}
