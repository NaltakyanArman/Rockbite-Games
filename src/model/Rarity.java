package model;

/**
 * Enum representing different levels of rarity for items.
 * Provides methods to get the next and previous rarity levels.
 */

public enum Rarity {
    COMMON,
    GREAT,
    RARE,
    EPIC,
    EPIC_1,
    EPIC_2,
    LEGENDARY;

    private static final Rarity[] VALUES = values();

    /**
     * Returns the next rarity level.
     *
     * @return The next rarity level, or null if the current one is LEGENDARY.
     */

    public Rarity getNextRarity() {
        int ordinal = ordinal();
        return ordinal < VALUES.length - 1 ? VALUES[ordinal + 1] : null; // LEGENDARY is the highest
    }

    /**
     * Returns the previous rarity level.
     *
     * @return The previous rarity level, or null if the current one is COMMON.
     */

    public Rarity getPrevRarity() {
        int ordinal = ordinal();
        return ordinal > 0 ? VALUES[ordinal - 1] : null; // COMMON is the lowest
    }
}
