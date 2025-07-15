package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, String> {
}
