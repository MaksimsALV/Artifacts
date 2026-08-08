package com.artifacts.game.service;

import com.artifacts.api.caching.CacheBankItems;
import com.artifacts.api.caching.CacheMyCharacters;
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
    private final CacheMyCharacters cacheMyCharacters;
    private final CacheBankItems cacheBankItems;
    private final Sleep sleep;

    public void depositItemsToBank(MyCharacters character, List<SimpleItemSchema> itemsDepositPayload) {
        var response = actionDepositBankItem.deposit(character, itemsDepositPayload);
        var cooldown = response.getBody().getData().getCooldown().getRemainingSeconds();
        var characterData = response.getBody().getData().getCharacter();
        var bankData = response.getBody().getData().getBank();

        cacheMyCharacters.updateCharacter(characterData);
        cacheBankItems.updateBank(bankData);
        sleep.sleep(character, cooldown);
    }
}
