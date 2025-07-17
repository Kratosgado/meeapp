package com.kratosgado.meeapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDto {
	private String id;
	private String name;
	private String creatorId;
	private String creatorName;
}
