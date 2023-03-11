package com.connect.campus.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_table")
@Data
@DynamicUpdate
@NoArgsConstructor
public class AdminEntity {
    @Id
    String adminId;
    @NonNull
    String password;


}
