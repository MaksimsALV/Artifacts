package com.artifacts.game.config;

public final class Characters {
    private Characters() {}
        private static final String WARRIOR = "MaxiTheGuy";
        private static final String GATHERER = "MaxGatherer";

        public static String getWarrior() {
            return WARRIOR;
        }
        public static String getGatherer() {
            return GATHERER;
        }
}
