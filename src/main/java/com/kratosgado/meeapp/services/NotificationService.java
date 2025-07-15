package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.repositories.NotificationRepo;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	private final NotificationRepo noteRepo;

	public NotificationService(NotificationRepo noteRepo) {
		this.noteRepo = noteRepo;
	}
}
