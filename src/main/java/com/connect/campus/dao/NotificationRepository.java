package com.connect.campus.dao;

import com.connect.campus.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    public List<NotificationEntity> findByNotificationTitle(String title);
}
