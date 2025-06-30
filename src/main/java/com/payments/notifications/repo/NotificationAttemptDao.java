package com.payments.notifications.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationAttemptDao extends JpaRepository<NotificationAttemptEntity, Long> {
}
