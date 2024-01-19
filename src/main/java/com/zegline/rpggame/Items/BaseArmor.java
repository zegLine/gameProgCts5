package main.java.com.zegline.rpggame.Items;

import main.java.com.zegline.rpggame.World;

import javax.swing.*;
import java.awt.*;

public abstract class BaseArmor extends Item {

    public int initialArmorBoost;
    public int armorHealth;
    public double armorMultiplier;

    public void equipArmor() {
        System.out.println("Equipping base armor");
    }

    @Override
    public void act(){
        equipArmor();
    }
    public BaseArmor(String name, int cost, String texture_name) {
        super(name, cost, texture_name);
        this.texture = new ImageIcon(World.class.getClassLoader().getResource("items/armor/" + texture_name)).getImage();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture,25 , 600, 32, 32,null);
    }
}
