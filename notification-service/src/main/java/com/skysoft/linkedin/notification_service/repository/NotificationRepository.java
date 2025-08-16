package com.skysoft.linkedin.notification_service.repository;

import com.skysoft.linkedin.notification_service.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
