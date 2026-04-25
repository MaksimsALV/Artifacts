package com.artifacts.api.logs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class ApiResponseLoggerTest {
    @Test
    void logResponseOnError_shouldLogOnlyOnError() {
        ApiResponseLogger apiResponseLogger = new ApiResponseLogger(new ObjectMapper());
        Logger logger = mock(Logger.class);

        ResponseEntity<String> okStatus = ResponseEntity.status(HttpStatus.OK).build();
        ResponseEntity<String> errorStatus = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        apiResponseLogger.logResponseOnError(logger, okStatus);
        verify(logger, never()).error(anyString(), any(), any());

        apiResponseLogger.logResponseOnError(logger, errorStatus);
        verify(logger).error(anyString(), any(), any());
    }
}
