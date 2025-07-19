package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Schema(description = "Event creation data")
public class CreateEventDto {
    @Schema(description = "Event title", example = "Summer Meetup", required = true)
    private String title;

    @Schema(description = "Event description", example = "A fun summer gathering for everyone")
    private String description;

    @Schema(description = "Event date and time", example = "2024-07-15T18:00:00", required = true)
    private Timestamp eventDate;

    @Schema(description = "Event location", example = "Central Park, New York")
    private String location;

    @Schema(description = "Maximum number of participants", example = "50")
    private Integer maxParticipants;

    @Schema(description = "Whether the event is public", example = "true", defaultValue = "true")
    private Boolean isPublic;

    @Schema(description = "Group ID if event belongs to a group", example = "123e4567-e89b-12d3-a456-426614174000")
    private String groupId;

    @Schema(description = "Event interests/tags", example = "[\"sports\", \"music\", \"food\"]")
    private Set<String> interests;
}