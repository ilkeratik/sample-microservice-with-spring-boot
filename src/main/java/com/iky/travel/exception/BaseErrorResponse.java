package com.iky.travel.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class BaseErrorResponse implements ErrorResponse {

  @Override
  public HttpStatusCode getStatusCode() {
    return HttpStatusCode.valueOf(500);
  }

  @Override
  public ProblemDetail getBody() {
    return ProblemDetail.forStatusAndDetail(getStatusCode(),
        "An exception occurred while processing your request");
  }
}
