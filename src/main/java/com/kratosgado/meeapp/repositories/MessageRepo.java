package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, String> {
}
