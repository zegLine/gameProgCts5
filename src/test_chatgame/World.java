package test_chatgame;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class World {

    public static UserAvatar simpleAvatar;

    private static int mula;

    public static Set<Item> items_equipped = new HashSet<>();

    public static Set<Item> items_available = new HashSet<>();

    public static Item item_in_hand = null;

    public static boolean enoughMoneyAndBuy(int amount) {
        if (amount > mula) return false;

        mula -= amount;

        return true;
    }

    public static int getMula(){
        return mula;
    }

    public static void setMula(int amount) {
        mula = amount;
    }

    private static int initialx = 10;
    private static int initialy = 80;
    public static void drawItemsEquipped(Graphics g) {
        if (items_equipped.isEmpty()) return;
        int y = initialy;
        for (Item i : items_equipped) {
            if (i == item_in_hand) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.drawString(i.name, initialx, y);
            y += 10;
        }
    }

    public static void equipItem(Item item) {
        for (Item i : items_equipped) {
            if (i == item) {
                item_in_hand = i;
            }
        }
    }

    public static void unEquipItem() {
        item_in_hand = null;
    }

}
