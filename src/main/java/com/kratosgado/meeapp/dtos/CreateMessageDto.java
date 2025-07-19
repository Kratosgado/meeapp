package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Message creation data")
public class CreateMessageDto {
    @Schema(description = "Message text content", example = "Hello everyone! How's it going?", required = true)
    private String text;

    @Schema(description = "Message image URL", example = "https://example.com/image.jpg")
    private String image;

    @Schema(description = "Group ID where the message will be sent", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private String groupId;
}