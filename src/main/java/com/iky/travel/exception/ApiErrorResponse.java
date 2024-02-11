package com.iky.travel.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ApiErrorResponse {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
}
