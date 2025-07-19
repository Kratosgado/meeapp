package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Post creation data")
public class CreatePostDto {
    @Schema(description = "Post content", example = "Just had an amazing experience at the tech conference!", required = true)
    private String content;

    @Schema(description = "Post image URL", example = "https://example.com/post-image.jpg")
    private String imageUrl;

    @Schema(description = "Group ID where the post will be created", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private String groupId;

    @Schema(description = "Post interests/tags", example = "[\"technology\", \"conference\", \"networking\"]")
    private Set<String> interests;
}