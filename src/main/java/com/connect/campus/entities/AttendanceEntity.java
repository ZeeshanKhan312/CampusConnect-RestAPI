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
public class AttendanceEntity {
    @Id
    String attendanceId;
    @NonNull
    String date;
    @NonNull
    String present;

    public AttendanceEntity() {
        attendanceId=null;
    }
}
