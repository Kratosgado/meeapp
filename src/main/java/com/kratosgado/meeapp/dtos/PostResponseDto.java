package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Data
@Schema(description = "Post response data")
public class PostResponseDto {
    @Schema(description = "Post ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Post content", example = "Just had an amazing experience at the tech conference!")
    private String content;

    @Schema(description = "Post image URL", example = "https://example.com/post-image.jpg")
    private String imageUrl;

    @Schema(description = "Post author information")
    private UserDto user;

    @Schema(description = "Group ID where the post was created", example = "123e4567-e89b-12d3-a456-426614174000")
    private String groupId;

    @Schema(description = "Post interests/tags", example = "[\"technology\", \"conference\", \"networking\"]")
    private Set<String> interests;

    @Schema(description = "Post creation date")
    private Date createdAt;

    @Schema(description = "Post last update date")
    private Date updatedAt;
}