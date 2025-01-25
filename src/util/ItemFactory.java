package util;

import model.Item;
import model.Rarity;

import java.util.Map;
import java.util.Random;

/**
 * Factory class for creating items with random rarities
 */

public class ItemFactory {
    private static final Random random = new Random();
    private static final Map<Rarity, Double> rarityWeights = Map.of(
            Rarity.COMMON, 0.5,
            Rarity.GREAT, 0.25,
            Rarity.RARE, 0.15,
            Rarity.EPIC, 0.08,
            Rarity.LEGENDARY, 0.02
    );

    /**
     * Creates a random item with a specified name and a random rarity.
     *
     * @param name The name of the item.
     * @return The created item.
     */

    public static Item createRandomItem(String name) {
        double rand = random.nextDouble();
        double cumulative = 0.0;

        for (Map.Entry<Rarity, Double> entry : rarityWeights.entrySet()) {
            cumulative += entry.getValue();
            if (rand <= cumulative) {
                return new Item(name, entry.getKey());
            }
        }

        return new Item(name, Rarity.COMMON);
    }
}