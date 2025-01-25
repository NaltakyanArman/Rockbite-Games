package service;

import exception.InsufficientItemsForUpgradeException;
import model.Item;
import model.Rarity;

import java.io.Serializable;
import java.util.*;


/**
 * This class allows you to add, remove, display, and upgrade items in the inventory.
 * I used Singleton design pattern
 */

public class Inventory implements Serializable {

    private static volatile Inventory instance;
    private final Map<String, Map<Rarity, List<Item>>> items;

    /**
     * Private constructor to initialize the inventory.
     */

    private Inventory() {
        items = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of the Inventory.
     *
     * @return The singleton Inventory instance.
     */

    public static Inventory getInstance() {
        if (instance == null) {
            synchronized (Inventory.class) { // Double-checked locking
                if (instance == null) {
                    instance = new Inventory();
                }
            }
        }
        return instance;
    }

    /**
     * Adds an item to the inventory.
     *
     * @param item The item to be added.
     */

    public void addItem(Item item) {
        items.computeIfAbsent(item.name(), k -> new HashMap<>())
                .computeIfAbsent(item.rarity(), k -> new ArrayList<>())
                .add(item);
    }

    public void removeItems(String name, Rarity rarity, int count) {
        Map<Rarity, List<Item>> rarityMap = items.get(name);
        if (rarityMap == null || rarityMap.get(rarity) == null) {
            throw new IllegalArgumentException();
        }

        List<Item> itemList = rarityMap.get(rarity);
        if (itemList.size() < count) {
            throw new IllegalArgumentException();
        }

        if (count > 0) {
            itemList.subList(0, count).clear();
        }

        if (itemList.isEmpty()) {
            rarityMap.remove(rarity);
            if (rarityMap.isEmpty()) {
                items.remove(name);
            }
        }

    }

    /**
     * Displays the current inventory
     *
     * @return A string representation of the inventory.
     */

    public String displayInventory() {
        StringBuilder sb = new StringBuilder("Inventory:\n");
        items.forEach((name, rarityMap) -> {
            sb.append(name).append(":\n");
            rarityMap.forEach((rarity, itemList) -> {
                sb.append("  ").append(rarity).append(" x").append(itemList.size()).append("\n");
            });
        });
        return sb.toString();
    }

    /**
     * Upgrades an item to the next rarity level.
     *
     * @param name            The name of the item.
     * @param expectedRarity The expected next rarity level.
     * @throws InsufficientItemsForUpgradeException If there are not enough items for the upgrade.
     */

    public void upgrade(String name, Rarity expectedRarity) {
        Rarity actualRarity = expectedRarity.getPrevRarity();
        Map<Rarity, List<Item>> rarityMap = items.get(name);
        if (rarityMap == null || rarityMap.get(actualRarity) == null) {
            throw new InsufficientItemsForUpgradeException();
        }

        List<Item> itemList = rarityMap.get(actualRarity);
        int reqCount = getRequiredCount(actualRarity);
        if (itemList.size() < reqCount && !handleEpicsUpgrade(actualRarity, expectedRarity, rarityMap, name)) {
            throw new InsufficientItemsForUpgradeException();
        }

        var item = createUpgradedItem(name, expectedRarity);
        instance.addItem(item);
        instance.removeItems(name, actualRarity, reqCount);

    }

    /**
     * Handles upgrades for EPIC items that have sub-levels (EPIC_1, EPIC_2).
     *
     * @param actualRarity   The actual rarity of the item.
     * @param expectedRarity The expected next rarity level.
     * @param rarityMap      The map of rarities for the item.
     * @param name           The name of the item.
     * @return True if the upgrade was successful, otherwise false.
     */

    private boolean handleEpicsUpgrade(Rarity actualRarity, Rarity expectedRarity, Map<Rarity, List<Item>> rarityMap, String name){
        if(actualRarity == Rarity.EPIC_1 && rarityMap.get(Rarity.EPIC) != null) {
            var item = createUpgradedItem(name, expectedRarity);
            instance.addItem(item);
            instance.removeItems(name, actualRarity, 1);
            instance.removeItems(name, Rarity.EPIC, 1);
            return true;
        }
        return false;
    }

    /**
     * Creates a new upgraded item.
     *
     * @param name            The name of the item.
     * @param expectedRarity The expected rarity of the upgraded item.
     * @return A new Item with the expected rarity.
     */

    private Item createUpgradedItem(String name, Rarity expectedRarity) {
        return new Item(name, expectedRarity);
    }

    /**
     * Returns the required number of items for an upgrade.
     *
     * @param rarity The rarity of the item.
     * @return The required count of items for the upgrade.
     */

    private static int getRequiredCount(Rarity rarity) {
        if (rarity == Rarity.EPIC || rarity == Rarity.EPIC_1) {
            return 2;
        } else {
            return 3;
        }
    }
}