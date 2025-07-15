package com.kratosgado.meeapp.repositories;

import org.postgresql.core.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, String> {
}
