# Inventory Management System

This project is a inventory management system with item rarity and upgrade functionality. The system allows adding, and upgrading items with various rarities. 

In the first image, you see the game's menu.

![image](https://github.com/user-attachments/assets/1a8646a9-a91e-439e-b43e-d1ddd3658991)

In the second image, when we press 1, we enter a item, and it randomly assigns a quality to it and adds it to our inventory.

![image](https://github.com/user-attachments/assets/aad02450-92b1-4f5d-b10e-2a27b7c81915)

When we press 2, we again enter the item's name, and we decide its quality.

![image](https://github.com/user-attachments/assets/6561016a-b5fc-4207-a757-3b584dc14e76)

When we press 3, we display the contents of the inventory.

![image](https://github.com/user-attachments/assets/813d4b65-ef64-4928-a20a-e8ee754ed5d9)

When we press 4, we save the contents of the inventory to a file.
When we press 5, we load the inventory.

When we press 6, we perform an item upgrade by entering the item's name and the quality we want to update it to, for example, upgrading from common to great.

![image](https://github.com/user-attachments/assets/a543c049-2e0c-48da-8963-f49d4e21b9e8)

We press 7 to exit the system.

P. S. I found it correct to have EPIC_1 and EPIC_2 as item fields because, under the given conditions, we would be storing unnecessary data in the form of the Item.upgradeCount field, while this way, we avoid storing meaningless data.


