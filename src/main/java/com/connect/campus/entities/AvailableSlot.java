package com.connect.campus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailableSlot {
    String slot;
    String day;
    String batchId;
    int teacherId;
}
