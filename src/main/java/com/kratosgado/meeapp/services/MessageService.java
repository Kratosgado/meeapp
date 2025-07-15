package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	private final MessageRepo messageRepo;

	@Autowired
	public MessageService(MessageRepo messageRepo) {
		this.messageRepo = messageRepo;
	}
}
