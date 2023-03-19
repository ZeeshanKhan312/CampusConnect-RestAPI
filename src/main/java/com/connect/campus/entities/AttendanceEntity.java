package com.connect.campus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "Attendance_table")
@Data
@NoArgsConstructor
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendance_id_generator")
    @SequenceGenerator(name = "attendance_id_generator",initialValue =100,allocationSize = 1,sequenceName = "attendance_table_sequence")
    int attendanceId;
    @NonNull
    String date;
    @NonNull
    String present;

}
