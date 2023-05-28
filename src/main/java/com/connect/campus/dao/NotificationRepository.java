package com.connect.campus.dao;

import com.connect.campus.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    @Query(value = "SELECT * FROM notification_table WHERE notification_title LIKE %:title%", nativeQuery = true)
    public List<NotificationEntity> findByNotificationTitle(String title);
}
