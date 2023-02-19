package com.connect.campus.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

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

  String scheduleId;

  @NonNull
  String currentSemester;


  public BatchEntity() {
    batchId = null;
  }
}
