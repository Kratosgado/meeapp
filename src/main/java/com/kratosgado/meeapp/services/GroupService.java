package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.dtos.GroupResponseDto;
import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.repositories.GroupRepo;
import com.kratosgado.meeapp.utils.SecurityUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
  private final Logger logger = LoggerFactory.getLogger(GroupService.class);
  private final GroupRepo groupRepo;
  private final UserService userService;

  @Autowired
  public GroupService(GroupRepo groupRepo, UserService userService) {
    this.groupRepo = groupRepo;
    this.userService = userService;
  }

  public GroupResponseDto createGroup(String name) {
    Group group = new Group(name, SecurityUtils.getCurrentUser());
    group = groupRepo.save(group);
    return convertToDto(group);
  }

  public void joinGroup(String id) {
    if (!groupRepo.existsById(id)) {
      throw new RuntimeException("Group not found");
    }
    groupRepo.addUserToGroup(id, SecurityUtils.getCurrentUserId());
  }

  public List<GroupResponseDto> getGroups() {
    return groupRepo.findAll().stream().map(this::convertToDto).toList();
  }

  public GroupResponseDto getGroupById(String id) {
    return groupRepo.findById(id).map(this::convertToDto).orElse(null);
  }

  private GroupResponseDto convertToDto(Group group) {
    return GroupResponseDto.builder()
        .id(group.getId())
        .name(group.getName())
        .creatorId(group.getCreator().getId())
        .creatorName(group.getCreator().getName())
        .build();
  }
}
