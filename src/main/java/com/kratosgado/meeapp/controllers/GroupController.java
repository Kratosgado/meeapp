package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.CreateGroupDto;
import com.kratosgado.meeapp.dtos.GroupResponseDto;
import com.kratosgado.meeapp.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
@Tag(name = "Groups")
@RequiredArgsConstructor
public class GroupController {

  private final GroupService groupService;

  @Operation(summary = "Create a new group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input or group name already exists"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @PostMapping
  public ResponseEntity<GroupResponseDto> createGroup(@RequestBody CreateGroupDto dto) {
    GroupResponseDto group = groupService.createGroup(dto);
    return ResponseEntity.ok(group);
  }

  @Operation(summary = "Update an existing group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input or group name already exists"),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Not authorized")
  })
  @PutMapping("/{groupId}")
  public ResponseEntity<GroupResponseDto> updateGroup(@PathVariable String groupId, @RequestBody CreateGroupDto dto) {
    GroupResponseDto group = groupService.updateGroup(groupId, dto);
    return ResponseEntity.ok(group);
  }

  @Operation(summary = "Delete a group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group deleted successfully"),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Not authorized")
  })
  @DeleteMapping("/{groupId}")
  public ResponseEntity<String> deleteGroup(@PathVariable String groupId) {
    groupService.deleteGroup(groupId);
    return ResponseEntity.ok("Group deleted successfully");
  }

  @Operation(summary = "Join a group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Joined group successfully"),
      @ApiResponse(responseCode = "400", description = "Already a member or group not found"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @PostMapping("/{groupId}/join")
  public ResponseEntity<String> joinGroup(@PathVariable String groupId) {
    groupService.joinGroup(groupId);
    return ResponseEntity.ok("Joined group successfully");
  }

  @Operation(summary = "Leave a group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Left group successfully"),
      @ApiResponse(responseCode = "400", description = "Not a member or creator cannot leave"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @PostMapping("/{groupId}/leave")
  public ResponseEntity<String> leaveGroup(@PathVariable String groupId) {
    groupService.leaveGroup(groupId);
    return ResponseEntity.ok("Left group successfully");
  }

  @Operation(summary = "Get all groups")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Groups found")
  })
  @GetMapping
  public ResponseEntity<List<GroupResponseDto>> getAllGroups() {
    List<GroupResponseDto> groups = groupService.getAllGroups();
    return ResponseEntity.ok(groups);
  }

  @Operation(summary = "Get public groups")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Groups found")
  })
  @GetMapping("/public")
  public ResponseEntity<List<GroupResponseDto>> getPublicGroups() {
    List<GroupResponseDto> groups = groupService.getPublicGroups();
    return ResponseEntity.ok(groups);
  }

  @Operation(summary = "Get my groups")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Groups found")
  })
  @GetMapping("/my-groups")
  public ResponseEntity<List<GroupResponseDto>> getMyGroups() {
    List<GroupResponseDto> groups = groupService.getMyGroups();
    return ResponseEntity.ok(groups);
  }

  @Operation(summary = "Get groups by user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Groups found")
  })
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<GroupResponseDto>> getGroupsByUser(@PathVariable String userId) {
    List<GroupResponseDto> groups = groupService.getGroupsByUser(userId);
    return ResponseEntity.ok(groups);
  }

  @Operation(summary = "Get group by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group found"),
      @ApiResponse(responseCode = "404", description = "Group not found")
  })
  @GetMapping("/{groupId}")
  public ResponseEntity<GroupResponseDto> getGroup(@PathVariable String groupId) {
    GroupResponseDto group = groupService.getGroupById(groupId);
    return ResponseEntity.ok(group);
  }

  @Operation(summary = "Get group count by user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Count retrieved")
  })
  @GetMapping("/user/{userId}/count")
  public ResponseEntity<Long> getGroupCountByUser(@PathVariable String userId) {
    Long count = groupService.getGroupCountByUser(userId);
    return ResponseEntity.ok(count);
  }

  @Operation(summary = "Get group count by creator")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Count retrieved")
  })
  @GetMapping("/creator/{userId}/count")
  public ResponseEntity<Long> getGroupCountByCreator(@PathVariable String userId) {
    Long count = groupService.getGroupCountByCreator(userId);
    return ResponseEntity.ok(count);
  }
}
