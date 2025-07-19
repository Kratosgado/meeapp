package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.CreatePostDto;
import com.kratosgado.meeapp.dtos.PostResponseDto;
import com.kratosgado.meeapp.services.PostService;
import com.kratosgado.meeapp.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@Tag(name = "Posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@Operation(summary = "Create a new post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Post created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input or user not in group"),
			@ApiResponse(responseCode = "401", description = "Not authenticated")
	})
	@PostMapping
	public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostDto dto) {
		String currentUserId = SecurityUtils.getCurrentUserId();
		PostResponseDto post = postService.createPost(dto, currentUserId);
		return ResponseEntity.ok(post);
	}

	@Operation(summary = "Delete a post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Post deleted successfully"),
			@ApiResponse(responseCode = "401", description = "Not authenticated"),
			@ApiResponse(responseCode = "403", description = "Not authorized")
	})
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable String postId) {
		String currentUserId = SecurityUtils.getCurrentUserId();
		postService.deletePost(postId, currentUserId);
		return ResponseEntity.ok("Post deleted successfully");
	}

	@Operation(summary = "Get post by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Post found"),
			@ApiResponse(responseCode = "404", description = "Post not found")
	})
	@GetMapping("/{postId}")
	public ResponseEntity<PostResponseDto> getPost(@PathVariable String postId) {
		PostResponseDto post = postService.getPostById(postId);
		return ResponseEntity.ok(post);
	}

	@Operation(summary = "Get posts by group with pagination")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Posts found")
	})
	@GetMapping("/group/{groupId}")
	public ResponseEntity<List<PostResponseDto>> getPostsByGroup(
			@PathVariable String groupId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		List<PostResponseDto> posts = postService.getPostsByGroup(groupId, page, size);
		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "Get recent posts by group")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Posts found")
	})
	@GetMapping("/group/{groupId}/recent")
	public ResponseEntity<List<PostResponseDto>> getRecentPostsByGroup(
			@PathVariable String groupId,
			@RequestParam(defaultValue = "10") int limit) {
		List<PostResponseDto> posts = postService.getRecentPostsByGroup(groupId, limit);
		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "Get posts by user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Posts found")
	})
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostResponseDto>> getPostsByUser(
			@PathVariable String userId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		List<PostResponseDto> posts = postService.getPostsByUser(userId, page, size);
		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "Get my posts")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Posts found")
	})
	@GetMapping("/my-posts")
	public ResponseEntity<List<PostResponseDto>> getMyPosts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		String currentUserId = SecurityUtils.getCurrentUserId();
		List<PostResponseDto> posts = postService.getPostsByUser(currentUserId, page, size);
		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "Get posts by interest")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Posts found")
	})
	@GetMapping("/interest/{interest}")
	public ResponseEntity<List<PostResponseDto>> getPostsByInterest(@PathVariable String interest) {
		List<PostResponseDto> posts = postService.getPostsByInterest(interest);
		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "Get post count by group")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Count retrieved")
	})
	@GetMapping("/group/{groupId}/count")
	public ResponseEntity<Long> getPostCountByGroup(@PathVariable String groupId) {
		Long count = postService.getPostCountByGroup(groupId);
		return ResponseEntity.ok(count);
	}
}
