package com.artifacts.api.service.account;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyAccountApi;
import org.openapitools.client.model.DataPageSimpleItemSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetBankItems {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<DataPageSimpleItemSchema> retrieveBankItems(String itemCode, Integer page, Integer size) {
        MyAccountApi myAccountApi = new MyAccountApi(apiClient);

        while (true) {
            try {
                ResponseEntity<DataPageSimpleItemSchema> response = myAccountApi.getBankItemsMyBankItemsGetWithHttpInfo(itemCode, page, size);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get Bank Items");
                retry.retry();
            }
        }
    }
}
