package main.java.com.zegline.rpggame;

import java.awt.*;

public class Commands {

    public static boolean buy(String[] parameters) {
        if (parameters.length >= 1) {
            String itemToBuy = parameters[0];
            System.out.println("Buying " + itemToBuy);

            for (Item i : World.items_available) {
                if (itemToBuy.equals(i.name)) {
                    if (World.enoughMoneyAndBuy(i.cost)) {
                        World.items_equipped.add(i);
                        return true;
                    }
                }
            }

            CommandHandler.commandError = "buy: item not found";
            System.out.println("buy: item not found");
            return false;
        } else {
            System.out.println("Usage: buy <item>");
            return false;
        }
    }

    public static boolean equip(String[] parameters) {
        if (parameters.length >= 1) {
            String itemToEquip = parameters[0];
            System.out.println("Equipping " + itemToEquip);

            for (Item i : World.items_equipped) {
                if (itemToEquip.equals(i.name)) {
                    World.equipItem(i);
                    return true;
                }
            }

            CommandHandler.commandError = "equip: item not found";
            System.out.println("equip: item not found");
            return false;
        } else {
            System.out.println("Usage: equip <item>");
            return false;
        }
    }

    public static boolean unequip(String[] parameters) {
        World.unEquipItem();
        return true;
    }

    public static boolean color(String[] parameters) {
        if (parameters.length >= 1) {
            String colorToSet = parameters[0].toLowerCase(); // Convert to lowercase for case-insensitive comparison
            Color newColor = null;

            switch (colorToSet) {
                case "red":
                    newColor = Color.RED;
                    break;
                case "green":
                    newColor = Color.GREEN;
                    break;
                case "pink":
                    newColor = Color.PINK;
                    break;
                default:
                    System.out.println("Invalid color. Usage: color <red, green, or pink>");
                    return false;
            }

            if (newColor != null) {
                System.out.println("Setting color to " + colorToSet);
                World.simpleAvatar.setC(newColor);
                return true;
            }
        }

        System.out.println("Usage: color <red, green, or pink>");
        return false;
    }


}
