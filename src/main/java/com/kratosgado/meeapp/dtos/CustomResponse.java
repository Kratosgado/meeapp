package com.kratosgado.meeapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CustomResponse {
  private final Object data;
  private final String message;
  private final HttpStatus status;
}
