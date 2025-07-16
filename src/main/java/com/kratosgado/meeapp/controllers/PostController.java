package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
@Tag(name = "Posts")
public class PostController {

	private final PostService service;

	@Autowired
	public PostController(PostService service) {
		this.service = service;
	}
}
