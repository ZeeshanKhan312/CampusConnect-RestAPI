package com.connect.campus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="NotificationTable")
@Data
@DynamicUpdate
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id_generator")
    @SequenceGenerator(name = "notification_id_generator", initialValue = 1, allocationSize = 1)
    int notificationId;
    @NonNull
    String notificationTitle;
    @NonNull
    String notificationMessage;

}
