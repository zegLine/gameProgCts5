package main.java.com.zegline.rpggame.GameEntity;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.Items.Item;
import main.java.com.zegline.rpggame.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.java.com.zegline.rpggame.MainGamePanel.retroFont;

public class ShopOwner extends GameEntity {

    private Image texture;
    public String message;
    public List<Item> items;

    public ShopOwner(int x, int y, int radius, String msg, List<Item> items, String texture_name) {
        super(x, y);
        this.radius = radius;
        this.message = msg;
        this.items = items;
        texture = new ImageIcon(World.class.getClassLoader().getResource("shopowners/" + texture_name)).getImage();
    }

    @Override
    public void draw(Graphics g) {
        //g.fillOval((int) x, (int) y, radius, radius);
        g.drawImage(texture, (int) x + 40, (int) y + 40, 60, 60, null);

        if (ChatGame.max.currentVendor == this) {
            g.setFont(retroFont.deriveFont(24.0f));
            g.drawString(message, (int) x + radius, (int) y + radius);

            g.setFont(retroFont.deriveFont(20.0f));
            g.drawString("I am selling", (int) x + radius, (int) y + radius + 20);

            int startingY = (int) y + radius + 20;

            for (Item i : items) {
                startingY += 15;
                g.setFont(retroFont.deriveFont(18.0f));
                g.drawString(i.name, (int) x + radius, startingY);
            }

        }
    }

    @Override
    public void update() {

    }
}
