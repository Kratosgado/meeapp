package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.dtos.CreateMessageDto;
import com.kratosgado.meeapp.dtos.MessageResponseDto;
import com.kratosgado.meeapp.dtos.UserDto;
import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.models.Message;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.GroupRepo;
import com.kratosgado.meeapp.repositories.MessageRepo;
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
public class MessageService {

	private final MessageRepo messageRepo;
	private final UserRepo userRepo;
	private final GroupRepo groupRepo;

	@Transactional
	public MessageResponseDto createMessage(CreateMessageDto dto, String currentUserId) {
		User user = userRepo.findById(currentUserId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Group group = groupRepo.findById(dto.getGroupId())
				.orElseThrow(() -> new RuntimeException("Group not found"));

		// Check if user is member of the group
		if (!groupRepo.isUserInGroup(dto.getGroupId(), currentUserId)) {
			throw new RuntimeException("User is not a member of this group");
		}

		Message message = new Message(dto.getText(), group, user);
		message.setImage(dto.getImage());

		Message savedMessage = messageRepo.save(message);
		return convertToDto(savedMessage);
	}

	@Transactional
	public void deleteMessage(String messageId, String currentUserId) {
		Message message = messageRepo.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));

		if (!message.getUser().getId().equals(currentUserId)) {
			throw new RuntimeException("Only message author can delete the message");
		}

		messageRepo.delete(message);
	}

	public MessageResponseDto getMessageById(String messageId) {
		Message message = messageRepo.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));
		return convertToDto(message);
	}

	public List<MessageResponseDto> getMessagesByGroup(String groupId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Message> messages = messageRepo.findByGroupIdOrderByCreatedAtDesc(groupId, pageable);
		return messages.getContent().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public List<MessageResponseDto> getRecentMessagesByGroup(String groupId, int limit) {
		Pageable pageable = PageRequest.of(0, limit);
		List<Message> messages = messageRepo.findRecentMessagesByGroup(groupId, pageable);
		return messages.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public List<MessageResponseDto> getMessagesByUser(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Message> messages = messageRepo.findByUserIdOrderByCreatedAtDesc(userId);
		return messages.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public Long getMessageCountByGroup(String groupId) {
		return messageRepo.countMessagesByGroup(groupId);
	}

	private MessageResponseDto convertToDto(Message message) {
		MessageResponseDto dto = new MessageResponseDto();
		dto.setId(message.getId());
		dto.setText(message.getText());
		dto.setImage(message.getImage());
		dto.setGroupId(message.getGroup().getId());
		dto.setCreatedAt(message.getCreatedAt());
		dto.setUpdatedAt(message.getUpdatedAt());

		// Convert user
		UserDto userDto = new UserDto();
		userDto.setId(message.getUser().getId());
		userDto.setName(message.getUser().getName());
		userDto.setEmail(message.getUser().getEmail());
		userDto.setProfilePicture(message.getUser().getProfilePicture());
		dto.setUser(userDto);

		return dto;
	}
}
