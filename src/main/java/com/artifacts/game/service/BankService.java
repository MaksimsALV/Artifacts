package com.artifacts.game.service;

import com.artifacts.api.service.mycharacters.ActionDepositBankItem;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Sleep;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.SimpleItemSchema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {
    private final ActionDepositBankItem actionDepositBankItem;
    private final Sleep sleep;

    public void depositItemsToBank(MyCharacters character, List<SimpleItemSchema> itemsDepositPayload) {
        var response = actionDepositBankItem.deposit(character, itemsDepositPayload);
        var cooldown = response.getBody().getData().getCooldown().getRemainingSeconds();

        sleep.sleep(character, cooldown);
    }
}
