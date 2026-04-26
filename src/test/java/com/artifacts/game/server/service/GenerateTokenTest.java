package com.artifacts.game.server.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.TokenApi;
import org.openapitools.client.model.TokenResponseSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
            new GenerateToken(apiClient, mock(ApiResponseLogger.class), mock(Retry.class))
                    .generateToken();

            verify(apiClient).setBearerToken("test-token");
        }
    }

    @Test
    void generateToken_shouldRetry_whenHttpStatusIsNotOk() {
        token.setToken("test-token");

        ResponseEntity<TokenResponseSchema> badResponse =
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ResponseEntity<TokenResponseSchema> okResponse =
                new ResponseEntity<>(token, HttpStatus.OK);

        try (MockedConstruction<TokenApi> ignored = mockConstruction(
                TokenApi.class,
                (mock, context) -> when(mock.generateTokenTokenPostWithHttpInfo())
                        .thenReturn(badResponse)
                        .thenReturn(okResponse)
        )) {
            new GenerateToken(apiClient, apiResponseLogger, retry).generateToken();

            verify(apiResponseLogger).logErrorResponse(any(), eq(badResponse));
            verify(retry).retry();
            verify(apiClient).setBearerToken("test-token");
        }
    }
}
