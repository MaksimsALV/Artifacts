package com.artifacts.game.library.characters;

public final class Characters {
    private Characters() {}
        private static final String WARRIOR = "MaxiTheGuy"; //The Hero, also a blacksmith that crafts his own weapons and gear, also Miner as needed
        private static final String MINER = "Bjorn"; //Strong miner from Scandinavia. Is well known for his jewelry-crafting skills. Can assist in killing/woodcutting too due to strength
        private static final String ALCHEMIST = "Linzy"; //Well known gnome alchemist from city named Dalaran. Knows all herbs.
        private static final String LUMBERJACK = "Axel"; //The master of Axe itself! Best lumberjack in the region. Can assist in killing/mining too due to strength
        private static final String CHEF = "Sushimiko"; //Master Chef of these lands, the well known sushi expert. Incredible talented fisher-woman that cooks great fish dishes.

        public static String getWarrior() {
            return WARRIOR;
        }
        public static String getMiner() {
            return MINER;
        }
        public static String getAlchemist() {
            return ALCHEMIST;
        }
        public static String getLumberjack() {
            return LUMBERJACK;
        }
        public static String getChef() {
            return CHEF;
        }
}
