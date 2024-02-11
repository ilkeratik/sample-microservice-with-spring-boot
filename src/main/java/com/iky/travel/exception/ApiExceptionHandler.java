package com.iky.travel.exception;

import com.iky.travel.exception.city.CityAddException;
import com.iky.travel.exception.city.CityAlreadyExistsException;
import com.iky.travel.exception.city.CityNotFoundException;
import com.iky.travel.exception.city.CityUpdateException;
import com.iky.travel.exception.common.RedisException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(CityNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> cityNotFoundHandler(CityNotFoundException ex,
      WebRequest request) {

    ApiErrorResponse error = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "City Not Found",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", "")
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CityAlreadyExistsException.class)
  public ResponseEntity<ApiErrorResponse> cityAlreadyExistHandler(CityAlreadyExistsException ex,
      WebRequest request) {

    ApiErrorResponse error = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "City Already Exists",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", "")
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(CityAddException.class)
  public ResponseEntity<ApiErrorResponse> cityAddExceptionHandler(CityAddException ex,
      WebRequest request) {

    ApiErrorResponse error = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "City Add Exception",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", "")
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CityUpdateException.class)
  public ResponseEntity<ApiErrorResponse> cityUpdateExceptionHandler(CityUpdateException ex,
      WebRequest request) {

    ApiErrorResponse error = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "City Update Exception",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", "")
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RedisException.class)
  public ResponseEntity<ApiErrorResponse> cityUpdateExceptionHandler(RedisException ex,
      WebRequest request) {

    ApiErrorResponse error = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "City Update Exception",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", "")
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
