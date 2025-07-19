package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;

@Data
@Schema(description = "Message response data")
public class MessageResponseDto {
    @Schema(description = "Message ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Message text content", example = "Hello everyone! How's it going?")
    private String text;

    @Schema(description = "Message image URL", example = "https://example.com/image.jpg")
    private String image;

    @Schema(description = "Message author information")
    private UserDto user;

    @Schema(description = "Group ID where the message was sent", example = "123e4567-e89b-12d3-a456-426614174000")
    private String groupId;

    @Schema(description = "Message creation date")
    private Date createdAt;

    @Schema(description = "Message last update date")
    private Date updatedAt;
}