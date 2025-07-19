package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Schema(description = "Event update data")
public class UpdateEventDto {
    @Schema(description = "Event title", example = "Updated Summer Meetup")
    private String title;

    @Schema(description = "Event description", example = "An updated fun summer gathering for everyone")
    private String description;

    @Schema(description = "Event date and time", example = "2024-07-20T18:00:00")
    private Timestamp eventDate;

    @Schema(description = "Event location", example = "Updated Central Park, New York")
    private String location;

    @Schema(description = "Maximum number of participants", example = "75")
    private Integer maxParticipants;

    @Schema(description = "Whether the event is public", example = "true")
    private Boolean isPublic;

    @Schema(description = "Event interests/tags", example = "[\"sports\", \"music\", \"food\", \"networking\"]")
    private Set<String> interests;
}