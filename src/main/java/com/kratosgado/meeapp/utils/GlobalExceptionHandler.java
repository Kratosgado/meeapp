package com.kratosgado.meeapp.utils;

import com.kratosgado.meeapp.dtos.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<CustomResponse> handleApiException(ApiException ex) {
    CustomResponse res = new CustomResponse(null, ex.getMessage(), ex.getStatus());
    return new ResponseEntity<>(res, ex.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomResponse> handleGlobalException(Exception ex) {
    CustomResponse response =
        new CustomResponse(
            null,
            "An unexpected error occurred: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
