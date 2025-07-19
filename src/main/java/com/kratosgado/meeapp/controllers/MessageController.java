package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.CreateMessageDto;
import com.kratosgado.meeapp.dtos.MessageResponseDto;
import com.kratosgado.meeapp.services.MessageService;
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
@RequestMapping("api/messages")
@Tag(name = "Messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Send a message to a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user not in group"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping
    public ResponseEntity<MessageResponseDto> sendMessage(@RequestBody CreateMessageDto dto) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        MessageResponseDto message = messageService.createMessage(dto, currentUserId);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Delete a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        messageService.deleteMessage(messageId, currentUserId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    @Operation(summary = "Get message by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message found"),
            @ApiResponse(responseCode = "404", description = "Message not found")
    })
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponseDto> getMessage(@PathVariable String messageId) {
        MessageResponseDto message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Get messages by group with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages found")
    })
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<MessageResponseDto>> getMessagesByGroup(
            @PathVariable String groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<MessageResponseDto> messages = messageService.getMessagesByGroup(groupId, page, size);
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Get recent messages by group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages found")
    })
    @GetMapping("/group/{groupId}/recent")
    public ResponseEntity<List<MessageResponseDto>> getRecentMessagesByGroup(
            @PathVariable String groupId,
            @RequestParam(defaultValue = "10") int limit) {
        List<MessageResponseDto> messages = messageService.getRecentMessagesByGroup(groupId, limit);
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Get messages by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageResponseDto>> getMessagesByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<MessageResponseDto> messages = messageService.getMessagesByUser(userId, page, size);
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Get my messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages found")
    })
    @GetMapping("/my-messages")
    public ResponseEntity<List<MessageResponseDto>> getMyMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<MessageResponseDto> messages = messageService.getMessagesByUser(currentUserId, page, size);
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Get message count by group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved")
    })
    @GetMapping("/group/{groupId}/count")
    public ResponseEntity<Long> getMessageCountByGroup(@PathVariable String groupId) {
        Long count = messageService.getMessageCountByGroup(groupId);
        return ResponseEntity.ok(count);
    }
}