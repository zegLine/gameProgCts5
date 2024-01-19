package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.Items.Item;

import java.awt.*;

import static main.java.com.zegline.rpggame.UserAvatar.items_equipped;

public class Commands {

    public static boolean buy(String[] parameters) {
        if (parameters.length < 1) {
            System.out.println("Usage: buy <item>");
            return false;
        }

        if (ChatGame.max.currentVendor == null) {
            CommandHandler.commandError = "go to a shop";
            System.out.println("not in shop error");
            return false;
        }

        String itemToBuy = parameters[0];
        System.out.println("Buying " + itemToBuy);

        for (Item i : ChatGame.max.currentVendor.items) {
            if (itemToBuy.equals(i.name)) {
                if (UserAvatar.enoughMoneyAndBuy(i.cost)) {
                    items_equipped.add(i);
                    return true;
                } else {
                    CommandHandler.commandError = "not enough mula";
                    System.out.println("buy: no mula");
                    return false;
                }
            }
        }

        CommandHandler.commandError = "buy: item not found";
        System.out.println("buy: item not found");
        return false;

    }

    public static boolean equip(String[] parameters) {
        if (parameters.length >= 1) {
            String itemToEquip = parameters[0];
            System.out.println("Equipping " + itemToEquip);

            for (Item i : items_equipped) {
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
