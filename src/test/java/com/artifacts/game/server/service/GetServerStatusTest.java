package com.artifacts.game.server.service;

import org.junit.jupiter.api.Test;
import org.openapitools.client.model.StatusResponseSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GetServerStatusTest {
    @Test
    void serverIsUp_shouldReturnTrue_ifServerWorks() {
        GetServerStatus getServerStatus = new GetServerStatus(null, null) {
            @Override
            public ResponseEntity<StatusResponseSchema> getServerStatus() {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        };

        assertTrue(getServerStatus.serverIsUp());
    }

    @Test
    void serverIsUp_shouldReturnFalse_ifServerDoesNotWork() {
        GetServerStatus getServerStatus = new GetServerStatus(null, null) {
            @Override
            public ResponseEntity<StatusResponseSchema> getServerStatus() {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //any HttpStatus that is not OK
            }
        };

        assertFalse(getServerStatus.serverIsUp());
    }
}
