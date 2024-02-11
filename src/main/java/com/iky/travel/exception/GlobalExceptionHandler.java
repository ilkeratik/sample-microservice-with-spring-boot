package com.iky.travel.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    HashMap<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ValidationErrorResponse response = new ValidationErrorResponse
        (
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "There are validation errors.",
            errors
        );
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest request) {
    //encapsulating the error details
    ErrorResponse errorResponse = new BaseErrorResponse();

    log.error("Unhandled exception caught: ", ex);

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
