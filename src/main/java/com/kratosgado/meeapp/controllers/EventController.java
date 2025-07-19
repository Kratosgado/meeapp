package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.CreateEventDto;
import com.kratosgado.meeapp.dtos.EventResponseDto;
import com.kratosgado.meeapp.dtos.UpdateEventDto;
import com.kratosgado.meeapp.services.EventService;
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
@RequestMapping("api/events")
@Tag(name = "Events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody CreateEventDto dto) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        EventResponseDto event = eventService.createEvent(dto, currentUserId);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Update an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable String eventId, @RequestBody UpdateEventDto dto) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        EventResponseDto event = eventService.updateEvent(eventId, dto, currentUserId);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Delete an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable String eventId) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        eventService.deleteEvent(eventId, currentUserId);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @Operation(summary = "Join an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Joined event successfully"),
            @ApiResponse(responseCode = "400", description = "Event is full or already joined"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/{eventId}/join")
    public ResponseEntity<EventResponseDto> joinEvent(@PathVariable String eventId) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        EventResponseDto event = eventService.joinEvent(eventId, currentUserId);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Leave an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Left event successfully"),
            @ApiResponse(responseCode = "400", description = "Not participating in event"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/{eventId}/leave")
    public ResponseEntity<EventResponseDto> leaveEvent(@PathVariable String eventId) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        EventResponseDto event = eventService.leaveEvent(eventId, currentUserId);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Get event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable String eventId) {
        EventResponseDto event = eventService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Get upcoming public events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found")
    })
    @GetMapping("/public/upcoming")
    public ResponseEntity<List<EventResponseDto>> getUpcomingPublicEvents() {
        List<EventResponseDto> events = eventService.getUpcomingPublicEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get events by group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found")
    })
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<EventResponseDto>> getEventsByGroup(@PathVariable String groupId) {
        List<EventResponseDto> events = eventService.getEventsByGroup(groupId);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get upcoming events by group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found")
    })
    @GetMapping("/group/{groupId}/upcoming")
    public ResponseEntity<List<EventResponseDto>> getUpcomingGroupEvents(@PathVariable String groupId) {
        List<EventResponseDto> events = eventService.getUpcomingGroupEvents(groupId);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get events by user involvement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found")
    })
    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponseDto>> getMyEvents() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<EventResponseDto> events = eventService.getEventsByUserInvolvement(currentUserId);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get events created by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found")
    })
    @GetMapping("/created")
    public ResponseEntity<List<EventResponseDto>> getCreatedEvents() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<EventResponseDto> events = eventService.getEventsByCreator(currentUserId);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get events by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventResponseDto>> getEventsByUser(@PathVariable String userId) {
        List<EventResponseDto> events = eventService.getEventsByUserInvolvement(userId);
        return ResponseEntity.ok(events);
    }
}