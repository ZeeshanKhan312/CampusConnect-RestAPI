package com.connect.campus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "parent_table")
@Data
@NoArgsConstructor
public class ParentEntity {
    @Id
    @Column(name ="parent_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "parent_id_generator")
    @SequenceGenerator(name = "parent_id_generator",initialValue = 5000,allocationSize = 2,sequenceName = "parent_table_sequence")
    int parentId;

    @NonNull
    String password;
    @NonNull
    String parentEmail;
}
