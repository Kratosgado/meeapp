package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.dtos.CreateEventDto;
import com.kratosgado.meeapp.dtos.EventResponseDto;
import com.kratosgado.meeapp.dtos.UpdateEventDto;
import com.kratosgado.meeapp.dtos.UserDto;
import com.kratosgado.meeapp.models.Event;
import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.EventRepository;
import com.kratosgado.meeapp.repositories.GroupRepo;
import com.kratosgado.meeapp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;

    @Transactional
    public EventResponseDto createEvent(CreateEventDto dto, String currentUserId) {
        User creator = userRepo.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = new Event(dto.getTitle(), dto.getDescription(), dto.getEventDate(), dto.getLocation(), creator);
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setIsPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true);
        event.setInterests(dto.getInterests());

        if (dto.getGroupId() != null) {
            Group group = groupRepo.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            event.setGroup(group);
        }

        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    @Transactional
    public EventResponseDto updateEvent(String eventId, UpdateEventDto dto, String currentUserId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getCreator().getId().equals(currentUserId)) {
            throw new RuntimeException("Only event creator can update the event");
        }

        if (dto.getTitle() != null)
            event.setTitle(dto.getTitle());
        if (dto.getDescription() != null)
            event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null)
            event.setEventDate(dto.getEventDate());
        if (dto.getLocation() != null)
            event.setLocation(dto.getLocation());
        if (dto.getMaxParticipants() != null)
            event.setMaxParticipants(dto.getMaxParticipants());
        if (dto.getIsPublic() != null)
            event.setIsPublic(dto.getIsPublic());
        if (dto.getInterests() != null)
            event.setInterests(dto.getInterests());

        Event updatedEvent = eventRepository.save(event);
        return convertToDto(updatedEvent);
    }

    @Transactional
    public void deleteEvent(String eventId, String currentUserId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getCreator().getId().equals(currentUserId)) {
            throw new RuntimeException("Only event creator can delete the event");
        }

        eventRepository.delete(event);
    }

    @Transactional
    public EventResponseDto joinEvent(String eventId, String currentUserId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepo.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getMaxParticipants() != null &&
                event.getParticipants().size() >= event.getMaxParticipants()) {
            throw new RuntimeException("Event is full");
        }

        if (event.getParticipants().contains(user)) {
            throw new RuntimeException("User already joined the event");
        }

        event.getParticipants().add(user);
        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    @Transactional
    public EventResponseDto leaveEvent(String eventId, String currentUserId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepo.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getParticipants().contains(user)) {
            throw new RuntimeException("User is not participating in this event");
        }

        event.getParticipants().remove(user);
        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    public EventResponseDto getEventById(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return convertToDto(event);
    }

    public List<EventResponseDto> getUpcomingPublicEvents() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<Event> events = eventRepository.findUpcomingPublicEvents(currentTime);
        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> getEventsByGroup(String groupId) {
        List<Event> events = eventRepository.findByGroupId(groupId);
        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> getUpcomingGroupEvents(String groupId) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<Event> events = eventRepository.findUpcomingGroupEvents(groupId, currentTime);
        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> getEventsByUserInvolvement(String userId) {
        List<Event> events = eventRepository.findEventsByUserInvolvement(userId);
        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> getEventsByCreator(String userId) {
        User creator = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Event> events = eventRepository.findByCreator(creator);
        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private EventResponseDto convertToDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setLocation(event.getLocation());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setIsPublic(event.getIsPublic());
        dto.setInterests(event.getInterests());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());
        dto.setParticipantCount(event.getParticipants().size());

        // Convert creator
        UserDto creatorDto = new UserDto();
        creatorDto.setId(event.getCreator().getId());
        creatorDto.setName(event.getCreator().getName());
        creatorDto.setEmail(event.getCreator().getEmail());
        creatorDto.setProfilePicture(event.getCreator().getProfilePicture());
        dto.setCreator(creatorDto);

        // Convert group if exists
        if (event.getGroup() != null) {
            com.kratosgado.meeapp.dtos.GroupResponseDto groupDto = new com.kratosgado.meeapp.dtos.GroupResponseDto();
            groupDto.setId(event.getGroup().getId());
            groupDto.setName(event.getGroup().getName());
            groupDto.setDescription(event.getGroup().getDescription());
            groupDto.setIsPublic(event.getGroup().getIsPublic());
            dto.setGroup(groupDto);
        }

        // Convert participants
        Set<UserDto> participantDtos = event.getParticipants().stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setName(user.getName());
                    userDto.setEmail(user.getEmail());
                    userDto.setProfilePicture(user.getProfilePicture());
                    return userDto;
                })
                .collect(Collectors.toSet());
        dto.setParticipants(participantDtos);

        return dto;
    }
}