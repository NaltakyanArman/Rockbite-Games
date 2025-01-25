import exception.InsufficientItemsForUpgradeException;
import model.Item;
import model.Rarity;
import service.Inventory;
import service.InventoryAdapter;
import util.ItemFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();
        Scanner scanner = new Scanner(System.in);
        String filePath = "inventory.dat";

        while (true) {
            System.out.println("\nItem Upgrade System:");
            System.out.println("1. Add a random item");
            System.out.println("2. Add a specific item");
            System.out.println("3. Display inventory");
            System.out.println("4. Save inventory");
            System.out.println("5. Load inventory");
            System.out.println("6. Upgrade");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    Item item = ItemFactory.createRandomItem(name);
                    inventory.addItem(item);
                    System.out.println("Added: " + item);
                }
                case 2 -> {
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter rarity (COMMON, GREAT, RARE, EPIC, EPIC_1, EPIC_2, LEGENDARY): ");
                    String rarityInput = scanner.nextLine().toUpperCase();
                    try {
                        Rarity rarity = Rarity.valueOf(rarityInput);
                        Item item = new Item(name, rarity);
                        inventory.addItem(item);
                        System.out.println("Added: " + item);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid rarity!");
                    }
                }
                case 3 -> System.out.println(inventory.displayInventory());
                case 4 -> {
                    InventoryAdapter.saveInventory(inventory, filePath);
                    System.out.println("Inventory saved successfully!");
                }
                case 5 -> {
                    Inventory loadedInventory = InventoryAdapter.loadInventory(filePath);
                    System.out.println("Inventory loaded successfully!");
                    System.out.println(loadedInventory.displayInventory());
                }
                case 6 -> {
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter desirable rarity (COMMON, GREAT, RARE, EPIC, EPIC_1, EPIC_2, LEGENDARY): ");
                    String rarityInput = scanner.nextLine().toUpperCase();

                    try {
                        Rarity rarity = Rarity.valueOf(rarityInput);
                        inventory.upgrade(name, rarity);
                        System.out.println("Upgraded: " + name + ", " + rarity);
                    } catch (InsufficientItemsForUpgradeException|IllegalArgumentException e) {
                        System.out.println("Invalid arguments!");
                    }
                }
                case 7 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
