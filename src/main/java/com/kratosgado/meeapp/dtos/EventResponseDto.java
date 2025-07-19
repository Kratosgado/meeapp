package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Schema(description = "Event response data")
public class EventResponseDto {
    @Schema(description = "Event ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Event title", example = "Summer Meetup")
    private String title;

    @Schema(description = "Event description", example = "A fun summer gathering for everyone")
    private String description;

    @Schema(description = "Event date and time", example = "2024-07-15T18:00:00")
    private Timestamp eventDate;

    @Schema(description = "Event location", example = "Central Park, New York")
    private String location;

    @Schema(description = "Maximum number of participants", example = "50")
    private Integer maxParticipants;

    @Schema(description = "Whether the event is public", example = "true")
    private Boolean isPublic;

    @Schema(description = "Event creator information")
    private UserDto creator;

    @Schema(description = "Group information if event belongs to a group")
    private GroupResponseDto group;

    @Schema(description = "Event participants")
    private Set<UserDto> participants;

    @Schema(description = "Event interests/tags", example = "[\"sports\", \"music\", \"food\"]")
    private Set<String> interests;

    @Schema(description = "Event creation date")
    private Date createdAt;

    @Schema(description = "Event last update date")
    private Date updatedAt;

    @Schema(description = "Number of participants", example = "25")
    private Integer participantCount;
}