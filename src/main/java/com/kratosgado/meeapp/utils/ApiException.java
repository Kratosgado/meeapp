package com.kratosgado.meeapp.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {
  private final HttpStatus status;

  public ApiException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
