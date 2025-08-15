/*
package com.artifacts.tools;

import com.artifacts.game.endpoints.characters.GetCharacter;

import java.time.Instant;

public class Converter {
    public static long SecondsToMillisConverter(double seconds) {
        return (long) (seconds * 1000);
    }

    //todo need to think if I need this converter even. Some endpoints convert seconds to millis and other way around via seconds * millis or millis / seconds.
    //todo maybe in good practice i should use a converter like this instead of writing above mentioned equation in every endpoint
    public static long convertCooldownExpirationTimeToMillis() {
        var cooldownExpirationDateTime = GetCharacter.CHARACTER.get(0).get("cooldown_expiration");
        var cooldownExpirationDateTimeInMillis = Instant.parse(cooldownExpirationDateTime).toEpochMilli();
        return cooldownExpirationDateTimeInMillis;
    }
}

 */
