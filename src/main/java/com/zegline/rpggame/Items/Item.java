package main.java.com.zegline.rpggame.Items;

import java.awt.*;

public abstract class Item {

    public Image texture;

    public String name;
    public int cost;
    public String texture_name;
    public void act() {
        System.out.println("ACT called on father Item class");
    }

    public Item(String name, int cost, String texture_name) {
        this.name = name;
        this.cost = cost;
        this.texture_name = texture_name;
    }

    public void draw(Graphics g) {
        System.out.println("Called DRAW on father Item class");
    }
}
