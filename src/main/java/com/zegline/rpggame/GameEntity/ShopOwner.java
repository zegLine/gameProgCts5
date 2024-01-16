package main.java.com.zegline.rpggame.GameEntity;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.Item;

import java.awt.*;
import java.util.List;

public class ShopOwner extends GameEntity {

    public String message;
    public List<Item> items;

    public ShopOwner(int x, int y, int radius, String msg, List<Item> items) {
        super(x, y);
        this.radius = radius;
        this.message = msg;
        this.items = items;
    }

    @Override
    public void draw(Graphics g) {
        g.fillOval((int) x, (int) y, radius, radius);

        if (ChatGame.max.currentVendor == this) {
            g.drawString(message, (int) x, (int) y);
        }
    }

    @Override
    public void update() {

    }
}
