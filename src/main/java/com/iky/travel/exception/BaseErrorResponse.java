package com.iky.travel.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatusCode;


public record BaseErrorResponse(LocalDateTime timestamp, String errorMessage, int httpStatusCode,
                                HttpStatusCode httpStatusMessage, String requestPath) {

}
