package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ScheduleTable")
@DynamicUpdate
@Data
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_generator")
    @SequenceGenerator(name = "schedule_id_generator",initialValue =100,allocationSize = 1,sequenceName = "schedule_table_sequence")
    int scheduleId;
    @NonNull
    String commonId;
    @NonNull
    String day;
    String slot1;
    String slot2;
    String slot3;
    String slot4;
    String slot5;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    BatchEntity batch;
    public ScheduleEntity() {
        scheduleId=null;
    }
}
