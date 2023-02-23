package com.connect.campus.entities;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "BatchTable")
@Data
@DynamicUpdate
@Transactional
public class BatchEntity {

  @Column(name = "batch_id")
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

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "batch_id",referencedColumnName = "batch_id")
  List<ScheduleEntity> scheduleList;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "batch_id",referencedColumnName = "batch_id")
  List<ExamScheduleEntity> examScheduleList;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "batch_id",referencedColumnName = "batch_id")
  List<StudentEntity> studentList;

  public BatchEntity() {
    batchId = null;
  }
}
