package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, String> {
}
