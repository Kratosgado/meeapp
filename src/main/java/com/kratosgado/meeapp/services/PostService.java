package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.dtos.CreatePostDto;
import com.kratosgado.meeapp.dtos.PostResponseDto;
import com.kratosgado.meeapp.dtos.UserDto;
import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.models.Post;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.GroupRepo;
import com.kratosgado.meeapp.repositories.PostRepo;
import com.kratosgado.meeapp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepo postRepo;
	private final UserRepo userRepo;
	private final GroupRepo groupRepo;

	@Transactional
	public PostResponseDto createPost(CreatePostDto dto, String currentUserId) {
		User user = userRepo.findById(currentUserId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Group group = groupRepo.findById(dto.getGroupId())
				.orElseThrow(() -> new RuntimeException("Group not found"));

		// Check if user is member of the group
		if (!groupRepo.isUserInGroup(dto.getGroupId(), currentUserId)) {
			throw new RuntimeException("User is not a member of this group");
		}

		Post post = new Post(dto.getContent(), user, group);
		post.setImageUrl(dto.getImageUrl());
		post.setInterests(dto.getInterests());

		Post savedPost = postRepo.save(post);
		return convertToDto(savedPost);
	}

	@Transactional
	public void deletePost(String postId, String currentUserId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		if (!post.getUser().getId().equals(currentUserId)) {
			throw new RuntimeException("Only post author can delete the post");
		}

		postRepo.delete(post);
	}

	public PostResponseDto getPostById(String postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));
		return convertToDto(post);
	}

	public List<PostResponseDto> getPostsByGroup(String groupId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Post> posts = postRepo.findByGroupIdOrderByCreatedAtDesc(groupId, pageable);
		return posts.getContent().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public List<PostResponseDto> getRecentPostsByGroup(String groupId, int limit) {
		Pageable pageable = PageRequest.of(0, limit);
		List<Post> posts = postRepo.findRecentPostsByGroup(groupId, pageable);
		return posts.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public List<PostResponseDto> getPostsByUser(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Post> posts = postRepo.findByUserIdOrderByCreatedAtDesc(userId);
		return posts.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public List<PostResponseDto> getPostsByInterest(String interest) {
		List<Post> posts = postRepo.findByInterest(interest);
		return posts.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public Long getPostCountByGroup(String groupId) {
		return postRepo.countPostsByGroup(groupId);
	}

	private PostResponseDto convertToDto(Post post) {
		PostResponseDto dto = new PostResponseDto();
		dto.setId(post.getId());
		dto.setContent(post.getContent());
		dto.setImageUrl(post.getImageUrl());
		dto.setGroupId(post.getGroup().getId());
		dto.setInterests(post.getInterests());
		dto.setCreatedAt(post.getCreatedAt());
		dto.setUpdatedAt(post.getUpdatedAt());

		// Convert user
		UserDto userDto = new UserDto();
		userDto.setId(post.getUser().getId());
		userDto.setName(post.getUser().getName());
		userDto.setEmail(post.getUser().getEmail());
		userDto.setProfilePicture(post.getUser().getProfilePicture());
		dto.setUser(userDto);

		return dto;
	}
}
