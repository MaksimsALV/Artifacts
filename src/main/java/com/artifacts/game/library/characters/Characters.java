package com.artifacts.game.library.characters;

public final class Characters {
    private Characters() {}
        private static final String WARRIOR = "MaxiTheGuy";
        private static final String GATHERER = "Hjorn";
        private static final String FORGEMASTER = "Bjorn";

        public static String getWarrior() {
            return WARRIOR;
        }
        public static String getGatherer() {
            return GATHERER;
        }
        public static String getForgemaster() {
            return FORGEMASTER;
        }
}
