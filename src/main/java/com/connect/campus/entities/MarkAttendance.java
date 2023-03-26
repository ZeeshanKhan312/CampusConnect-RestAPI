package com.connect.campus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarkAttendance {
    AttendanceEntity attendance;
    int studentId;
}
