package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.CreateGroupDto;
import com.kratosgado.meeapp.dtos.GroupResponseDto;
import com.kratosgado.meeapp.services.GroupService;
import com.kratosgado.meeapp.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/groups")
@Tag(name = "Groups")
public class GroupController {

  private final GroupService groupService;
  private final MessageService messageService;

  @Autowired
  public GroupController(GroupService groupService, MessageService messageService) {
    this.groupService = groupService;
    this.messageService = messageService;
  }

  @Operation(summary = "Create a new group")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Group created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
      })
  @PostMapping
  public ResponseEntity<GroupResponseDto> createGroup(@RequestBody CreateGroupDto dto) {
    return ResponseEntity.ok(groupService.createGroup(dto.getName()));
  }

  @Operation(summary = "Get a group by ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Groups found"),
        @ApiResponse(responseCode = "404", description = "Groups not found")
      })
  @GetMapping
  public List<GroupResponseDto> getGroups() {
    return groupService.getGroups();
  }

  @PostMapping("/join/{groupId}")
  public ResponseEntity<String> joinGroup(@PathVariable String groupId) {
    // try {
    groupService.joinGroup(groupId);
    return ResponseEntity.ok("Joined successfully");
    // } catch (Exception e) {
    // return ResponseEntity.notFound().build();
    // }
  }

  @Operation(summary = "Get a group by ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Group found"),
        @ApiResponse(responseCode = "404", description = "Group not found")
      })
  @GetMapping("/{id}")
  public ResponseEntity<GroupResponseDto> getGroup(@PathVariable String id) {
    return ResponseEntity.ok(groupService.getGroupById(id));
  }
}
