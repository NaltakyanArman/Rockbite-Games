package service;

import java.io.*;

/**
 * Utility class to handle saving and loading the inventory to/from a file.
 */

public class InventoryAdapter {

    /**
     * Saves the inventory to a file.
     *
     * @param inventory The inventory to be saved.
     * @param filePath  The path to the file where the inventory will be saved.
     */

    public static void saveInventory(Inventory inventory, String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                if(!file.createNewFile()){
                    System.err.println("Failed to create the file: " + filePath);
                    return;
                }
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(inventory);
            }
        }
        catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads the inventory from a file.
     *
     * @param filePath The path to the file from which the inventory will be loaded.
     * @return The loaded inventory, or a fresh instance if loading fails.
     */

    public static Inventory loadInventory(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Inventory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
            return Inventory.getInstance(); // Return a fresh inventory on failure
        }
    }
}