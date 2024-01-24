package main.java.com.zegline.rpggame.Items;

import main.java.com.zegline.rpggame.SoundEngine;
import main.java.com.zegline.rpggame.World;

import javax.swing.*;
import java.awt.*;

public abstract class BaseGun extends Item {

    public void shoot() {
        System.out.println("Shooting base gun");
    }

    @Override
    public void act(){
        shoot();
        SoundEngine.playOneShotEffect("sfx/shot.wav");
    }

    public BaseGun(String name, int cost, String texture_name) {
        super(name, cost, texture_name);
        this.texture = new ImageIcon(World.class.getClassLoader().getResource("items/guns/" + texture_name)).getImage();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture,25 , 25, 32, 32,null);
    }
}
