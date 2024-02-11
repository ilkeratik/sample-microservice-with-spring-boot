package com.iky.travel.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ValidationErrorResponse {

  private LocalDateTime timestamp;
  private int status;
  private String message;
  private HashMap<String, String> errors;
}
