package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.dtos.CreateGroupDto;
import com.kratosgado.meeapp.dtos.GroupResponseDto;
import com.kratosgado.meeapp.dtos.UserDto;
import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.GroupRepo;
import com.kratosgado.meeapp.repositories.MessageRepo;
import com.kratosgado.meeapp.repositories.PostRepo;
import com.kratosgado.meeapp.repositories.UserRepo;
import com.kratosgado.meeapp.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final Logger logger = LoggerFactory.getLogger(GroupService.class);
  private final GroupRepo groupRepo;
  private final UserRepo userRepo;
  private final MessageRepo messageRepo;
  private final PostRepo postRepo;

  @Transactional
  public GroupResponseDto createGroup(CreateGroupDto dto) {
    User creator = SecurityUtils.getCurrentUser();

    // Check if group name already exists
    if (groupRepo.findByName(dto.getName()).isPresent()) {
      throw new RuntimeException("Group name already exists");
    }

    Group group = new Group(dto.getName(), creator);
    group.setDescription(dto.getDescription());
    group.setIsPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true);

    Group savedGroup = groupRepo.save(group);
    return convertToDto(savedGroup);
  }

  @Transactional
  public GroupResponseDto updateGroup(String groupId, CreateGroupDto dto) {
    Group group = groupRepo.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Group not found"));

    if (!group.getCreator().getId().equals(SecurityUtils.getCurrentUserId())) {
      throw new RuntimeException("Only group creator can update the group");
    }

    if (dto.getName() != null) {
      // Check if new name already exists (excluding current group)
      groupRepo.findByName(dto.getName())
          .filter(existingGroup -> !existingGroup.getId().equals(groupId))
          .ifPresent(existingGroup -> {
            throw new RuntimeException("Group name already exists");
          });
      group.setName(dto.getName());
    }

    if (dto.getDescription() != null)
      group.setDescription(dto.getDescription());
    if (dto.getIsPublic() != null)
      group.setIsPublic(dto.getIsPublic());

    Group updatedGroup = groupRepo.save(group);
    return convertToDto(updatedGroup);
  }

  @Transactional
  public void deleteGroup(String groupId) {
    Group group = groupRepo.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Group not found"));

    if (!group.getCreator().getId().equals(SecurityUtils.getCurrentUserId())) {
      throw new RuntimeException("Only group creator can delete the group");
    }

    groupRepo.delete(group);
  }

  @Transactional
  public void joinGroup(String groupId) {
    if (!groupRepo.existsById(groupId)) {
      throw new RuntimeException("Group not found");
    }

    String currentUserId = SecurityUtils.getCurrentUserId();

    // Check if user is already in the group
    if (groupRepo.isUserInGroup(groupId, currentUserId)) {
      throw new RuntimeException("User is already a member of this group");
    }

    groupRepo.addUserToGroup(groupId, currentUserId);
  }

  @Transactional
  public void leaveGroup(String groupId) {
    if (!groupRepo.existsById(groupId)) {
      throw new RuntimeException("Group not found");
    }

    String currentUserId = SecurityUtils.getCurrentUserId();

    // Check if user is in the group
    if (!groupRepo.isUserInGroup(groupId, currentUserId)) {
      throw new RuntimeException("User is not a member of this group");
    }

    // Check if user is the creator
    Group group = groupRepo.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Group not found"));

    if (group.getCreator().getId().equals(currentUserId)) {
      throw new RuntimeException(
          "Group creator cannot leave the group. Transfer ownership or delete the group instead.");
    }

    groupRepo.removeUserFromGroup(groupId, currentUserId);
  }

  public List<GroupResponseDto> getAllGroups() {
    return groupRepo.findAll().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  public List<GroupResponseDto> getPublicGroups() {
    return groupRepo.findByIsPublicTrue().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  public List<GroupResponseDto> getGroupsByUser(String userId) {
    return groupRepo.findGroupsByUser(userId).stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  public List<GroupResponseDto> getMyGroups() {
    String currentUserId = SecurityUtils.getCurrentUserId();
    return getGroupsByUser(currentUserId);
  }

  public GroupResponseDto getGroupById(String groupId) {
    Group group = groupRepo.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Group not found"));
    return convertToDto(group);
  }

  public Long getGroupCountByUser(String userId) {
    return groupRepo.countGroupsByUser(userId);
  }

  public Long getGroupCountByCreator(String userId) {
    return groupRepo.countGroupsByCreator(userId);
  }

  private GroupResponseDto convertToDto(Group group) {
    GroupResponseDto dto = new GroupResponseDto();
    dto.setId(group.getId());
    dto.setName(group.getName());
    dto.setDescription(group.getDescription());
    dto.setIsPublic(group.getIsPublic());
    dto.setCreatedAt(new java.sql.Date(group.getCreatedAt().getTime()));
    dto.setUpdatedAt(new java.sql.Date(group.getUpdatedAt().getTime()));

    // Convert creator
    UserDto creatorDto = new UserDto();
    creatorDto.setId(group.getCreator().getId());
    creatorDto.setName(group.getCreator().getName());
    creatorDto.setEmail(group.getCreator().getEmail());
    creatorDto.setProfilePicture(group.getCreator().getProfilePicture());
    dto.setCreator(creatorDto);

    // Convert users
    List<UserDto> userDtos = group.getUsers().stream()
        .map(user -> {
          UserDto userDto = new UserDto();
          userDto.setId(user.getId());
          userDto.setName(user.getName());
          userDto.setEmail(user.getEmail());
          userDto.setProfilePicture(user.getProfilePicture());
          return userDto;
        })
        .collect(Collectors.toList());
    dto.setUsers(userDtos);

    // Set counts
    dto.setMemberCount((long) group.getUsers().size());
    dto.setMessageCount(messageRepo.countMessagesByGroup(group.getId()));
    dto.setPostCount(postRepo.countPostsByGroup(group.getId()));
    dto.setEventCount((long) group.getEvents().size());

    return dto;
  }
}
