package com.artifacts.api.service.mycharacters;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyCharactersApi;
import org.openapitools.client.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

import static com.artifacts.api.HttpCodes.*;

@Service
public class ActionDepositBankItem {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ActionDepositBankItem(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<BankItemTransactionResponseSchema> deposit(MyCharacters characters, List<SimpleItemSchema> items) {
        MyCharactersApi myCharactersApi = new MyCharactersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<BankItemTransactionResponseSchema> response = myCharactersApi.actionDepositBankItemMyNameActionBankDepositItemPostWithHttpInfo(characters.getName(), items);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Action Deposit Bank Item");
                var responseHttpCode = e.getStatusCode().value();

                if (responseHttpCode == NOT_FOUND ||
                        responseHttpCode == INVALID_PAYLOAD ||
                        responseHttpCode == CHARACTER_LOCKED ||
                        responseHttpCode == CHARACTER_ALREADY_MAP ||
                        responseHttpCode == CHARACTER_CONDITION_NOT_MET ||
                        responseHttpCode == CHARACTER_NOT_FOUND ||
                        responseHttpCode == CHARACTER_IN_COOLDOWN ||
                        responseHttpCode == NO_PATH_AVAILABLE_TO_THE_DESTINATION_MAP ||
                        responseHttpCode == THE_MAP_IS_BLOCKED_AND_CANNOT_BE_ACCESSED) {

                    return ResponseEntity.status(responseHttpCode).build();
                } else {
                    retry.retry();
                }
            }
        }
    }
    public boolean success(ResponseEntity<BankItemTransactionResponseSchema> response) {
        return response.getStatusCode().value() == SUCCESS;
    }

    public int cooldown(ResponseEntity<BankItemTransactionResponseSchema> response) {
        return response.getBody().getData().getCooldown().getRemainingSeconds();
    }
}
