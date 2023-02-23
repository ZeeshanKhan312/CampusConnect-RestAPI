package com.connect.campus.dao;

import com.connect.campus.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
    public NotificationEntity findByNotificationTitle(String title);
}
