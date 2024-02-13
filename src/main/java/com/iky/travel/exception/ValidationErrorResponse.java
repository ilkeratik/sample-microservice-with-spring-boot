package com.iky.travel.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorResponse {

  private LocalDateTime timestamp;
  private int httpStatusCode;
  private String errorMessage;
  private HashMap<String, String> fieldErrors;
}
