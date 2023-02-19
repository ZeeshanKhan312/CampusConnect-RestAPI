package com.connect.campus.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "BatchTable")
@Data
@DynamicUpdate
public class BatchEntity {

  @Column(name = "BatchID")
  @Id
  String batchId;
  @NonNull
  String courseName;
  @NonNull
  String courseYear;
  @NonNull
  String courseDuration;
  @NonNull
  String feesAmount;

  @NonNull
  String currentSemester;

  @OneToMany(mappedBy = "batch",cascade = CascadeType.ALL)
  List<ScheduleEntity> scheduleList;

  @OneToMany(mappedBy = "batch",cascade = CascadeType.ALL)
  List<ExamScheduleEntity> examScheduleList;

  @OneToMany(mappedBy = "batch",cascade = CascadeType.ALL)
  List<StudentEntity> studentList;
  public BatchEntity() {
    batchId = null;
  }
}
