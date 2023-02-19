package com.connect.campus.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

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
