package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, String> {
}
