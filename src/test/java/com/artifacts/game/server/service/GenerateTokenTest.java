package com.artifacts.game.server.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.api.service.GenerateToken;
import com.artifacts.tools.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.TokenApi;
import org.openapitools.client.model.TokenResponseSchema;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

public class GenerateTokenTest {
    private ApiClient apiClient;
    private ApiResponseLogger apiResponseLogger;
    private Retry retry;
    private TokenResponseSchema token;

    @BeforeEach
    void setUp() {
        apiClient = mock(ApiClient.class);
        apiResponseLogger = mock(ApiResponseLogger.class);
        retry = mock(Retry.class);
        token = new TokenResponseSchema();
    }

    @Test
    void generateToken_shouldSetBearerToken_whenTokenIsReturned() {
        token.setToken("test-token");

        ResponseEntity<TokenResponseSchema> response =
                new ResponseEntity<>(token, HttpStatus.OK);

        try (MockedConstruction<TokenApi> mockedTokenApi = mockConstruction(
                TokenApi.class,
                (mock, context) -> when(mock.generateTokenTokenPostWithHttpInfo()).thenReturn(response)
        )) {
            new GenerateToken(apiClient, apiResponseLogger, retry)
                    .generateToken();

            verify(apiClient).setBearerToken("test-token");
        }
    }

    @Test
    void generateToken_shouldRetry_whenApiThrowsError() {
        token.setToken("test-token");

        HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                HttpHeaders.EMPTY,
                "{\"error\":\"bad request\"}".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        ResponseEntity<TokenResponseSchema> okResponse =
                new ResponseEntity<>(token, HttpStatus.OK);

        try (MockedConstruction<TokenApi> ignored = mockConstruction(
                TokenApi.class,
                (mock, context) -> when(mock.generateTokenTokenPostWithHttpInfo())
                        .thenThrow(exception)
                        .thenReturn(okResponse)
        )) {
            new GenerateToken(apiClient, apiResponseLogger, retry).generateToken();

            verify(apiResponseLogger).logErrorResponse(any(Logger.class), eq(exception));
            verify(retry).retry();
            verify(apiClient).setBearerToken("test-token");
        }
    }
}