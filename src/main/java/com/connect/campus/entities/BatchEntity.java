package com.connect.campus.entities;


import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BatchTable")
@Data
@DynamicUpdate
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
  List<ScheduleEntity> schedules;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "batch_id",referencedColumnName = "batch_id")
  List<ExamScheduleEntity> examSchedules;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "batch_id",referencedColumnName = "batch_id")
  List<StudentEntity> students;

  public BatchEntity() {
    batchId = null;
  }
}
