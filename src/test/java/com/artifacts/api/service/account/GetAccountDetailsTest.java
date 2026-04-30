package com.artifacts.api.service.account;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyAccountApi;
import org.openapitools.client.model.MyAccountDetailsSchema;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAccountDetailsTest {
    private ApiClient apiClient;
    private ApiResponseLogger apiResponseLogger;
    private Retry retry;
    private MyAccountDetailsSchema accountDetails;

    @BeforeEach
    void setUp() {
        apiClient = mock(ApiClient.class);
        apiResponseLogger = mock(ApiResponseLogger.class);
        retry = mock(Retry.class);
        accountDetails = mock(MyAccountDetailsSchema.class, RETURNS_DEEP_STUBS);
    }

    @Test
    void retrieveAccountDetails_shouldReturnResponse_whenStatusIsSuccess() {
        ResponseEntity<MyAccountDetailsSchema> response =
                new ResponseEntity<>(accountDetails, HttpStatus.OK);

        try (MockedConstruction<MyAccountApi> ignored = mockConstruction(
                MyAccountApi.class,
                (mock, context) -> when(mock.getAccountDetailsMyDetailsGetWithHttpInfo())
                        .thenReturn(response)
        )) {
            ResponseEntity<MyAccountDetailsSchema> actualResponse =
                    new GetAccountDetails(apiClient, apiResponseLogger, retry)
                            .retrieveAccountDetails();

            assertSame(response, actualResponse);
            verifyNoInteractions(apiResponseLogger);
            verifyNoInteractions(retry);
        }
    }

    @Test
    void retrieveAccountDetails_shouldRetry_whenApiThrowsError() {
        HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                HttpHeaders.EMPTY,
                "{\"error\":\"bad request\"}".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        ResponseEntity<MyAccountDetailsSchema> okResponse =
                new ResponseEntity<>(accountDetails, HttpStatus.OK);

        try (MockedConstruction<MyAccountApi> ignored = mockConstruction(
                MyAccountApi.class,
                (mock, context) -> when(mock.getAccountDetailsMyDetailsGetWithHttpInfo())
                        .thenThrow(exception)
                        .thenReturn(okResponse)
        )) {
            ResponseEntity<MyAccountDetailsSchema> actualResponse =
                    new GetAccountDetails(apiClient, apiResponseLogger, retry)
                            .retrieveAccountDetails();

            assertSame(okResponse, actualResponse);
            verify(apiResponseLogger).logErrorResponse(any(Logger.class), eq(exception));
            verify(retry).retry();
        }
    }

    @Test
    void retrieveAccountUsername_shouldReturnUsernameFromAccountDetails() {
        when(accountDetails.getData().getUsername()).thenReturn("Max");

        ResponseEntity<MyAccountDetailsSchema> response =
                new ResponseEntity<>(accountDetails, HttpStatus.OK);

        try (MockedConstruction<MyAccountApi> ignored = mockConstruction(
                MyAccountApi.class,
                (mock, context) -> when(mock.getAccountDetailsMyDetailsGetWithHttpInfo())
                        .thenReturn(response)
        )) {
            String username =
                    new GetAccountDetails(apiClient, apiResponseLogger, retry)
                            .retrieveAccountUsername();

            assertEquals("Max", username);
        }
    }

    @Test
    void retrieveAccountDetails_wrongTokenStatusCodeShouldBe452() {
        HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatusCode.valueOf(452),
                "Wrong Token",
                HttpHeaders.EMPTY,
                "{\"error\":\"wrong token\"}".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        assertEquals(452, exception.getStatusCode().value());
    }
}