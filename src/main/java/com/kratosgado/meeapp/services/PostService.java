package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
	private final PostRepo postRepo;

	@Autowired
	public PostService(PostRepo postRepo) {
		this.postRepo = postRepo;
	}
}
