package com.artifacts.api.service.account;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.AccountsApi;
import org.openapitools.client.model.CharactersListSchema;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAccountCharactersTest {
    private ApiClient apiClient;
    private ApiResponseLogger apiResponseLogger;
    private Retry retry;
    private GetAccountDetails getAccountDetails;
    private CharactersListSchema charactersList;

    @BeforeEach
    void setUp() {
        apiClient = mock(ApiClient.class);
        apiResponseLogger = mock(ApiResponseLogger.class);
        retry = mock(Retry.class);
        getAccountDetails = mock(GetAccountDetails.class);
        charactersList = new CharactersListSchema();
    }

    @Test
    void retrieveAccountCharacters_shouldReturnStatusOk_whenStatusIsSuccess() {
        ResponseEntity<CharactersListSchema> response =
                new ResponseEntity<>(charactersList, HttpStatus.OK);

        try (MockedConstruction<AccountsApi> ignored = mockConstruction(
                AccountsApi.class,
                (mock, context) -> when(mock.getAccountCharactersAccountsAccountCharactersGetWithHttpInfo(nullable(String.class)))
                        .thenReturn(response)
        )) {
            ResponseEntity<CharactersListSchema> actualResponse =
                    new GetAccountCharacters(apiClient, apiResponseLogger, retry, getAccountDetails)
                            .retrieveAccountCharacters();

            assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }
    }

    @Test
    void retrieveAccountCharacters_shouldReturnValidJson_whenStatusIsSuccess() {
        ResponseEntity<CharactersListSchema> response =
                new ResponseEntity<>(charactersList, HttpStatus.OK);

        try (MockedConstruction<AccountsApi> ignored = mockConstruction(
                AccountsApi.class,
                (mock, context) -> when(mock.getAccountCharactersAccountsAccountCharactersGetWithHttpInfo(nullable(String.class)))
                        .thenReturn(response)
        )) {
            ResponseEntity<CharactersListSchema> actualResponse =
                    new GetAccountCharacters(apiClient, apiResponseLogger, retry, getAccountDetails)
                            .retrieveAccountCharacters();

            ObjectMapper objectMapper = new ObjectMapper();

            String json = assertDoesNotThrow(() ->
                    objectMapper.writeValueAsString(actualResponse.getBody())
            );

            assertDoesNotThrow(() -> objectMapper.readTree(json));
        }
    }

    @Test
    void retrieveAccountCharacters_shouldRetry_whenApiThrowsError() {
        when(getAccountDetails.retrieveAccountUsername()).thenReturn("Max");

        HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                HttpHeaders.EMPTY,
                "{\"error\":\"bad request\"}".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        ResponseEntity<CharactersListSchema> response =
                new ResponseEntity<>(charactersList, HttpStatus.OK);

        try (MockedConstruction<AccountsApi> ignored = mockConstruction(
                AccountsApi.class,
                (mock, context) -> when(mock.getAccountCharactersAccountsAccountCharactersGetWithHttpInfo("Max"))
                        .thenThrow(exception)
                        .thenReturn(response)
        )) {
            ResponseEntity<CharactersListSchema> actualResponse =
                    new GetAccountCharacters(apiClient, apiResponseLogger, retry, getAccountDetails)
                            .retrieveAccountCharacters();

            assertSame(response, actualResponse);
            verify(apiResponseLogger).logErrorResponse(any(Logger.class), eq(exception));
            verify(retry).retry();
        }
    }
}