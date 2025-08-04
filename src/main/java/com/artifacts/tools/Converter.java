package com.artifacts.tools;

import com.artifacts.game.endpoints.characters.GetCharacter;

import java.time.Instant;

public class Converter {
    public static long SecondsToMillisConverter(double seconds) {
        return (long) (seconds * 1000);
    }

    //todo need to convert millis to seconds too, else this happens: Character is currently on cooldown for: 22392s
    public static long convertCooldownExpirationTimeToMillis() {
        var cooldownExpirationDateTime = GetCharacter.CHARACTER.get(0).get("cooldown_expiration");
        var cooldownExpirationDateTimeInMillis = Instant.parse(cooldownExpirationDateTime).toEpochMilli();
        //var convertedCurrentDateTimeToMillis = Instant.now().toEpochMilli();

        //var cooldownTimeInMillis = convertedCooldownExpirationDateTimeToMillis - convertedCurrentDateTimeToMillis;
        //return cooldownTimeInMillis;
        return cooldownExpirationDateTimeInMillis;
    }
}
