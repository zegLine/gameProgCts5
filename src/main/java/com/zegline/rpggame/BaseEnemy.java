package main.java.com.zegline.rpggame;

import java.awt.*;
import java.util.ArrayList;

public abstract class BaseEnemy {
    private int speed;



    private Color c;

    private int x;
    private int y;
    private int damage;
    private int radius;
    private ArrayList<BaseEnemy> parentList;

    public BaseEnemy(int x, int y, int level) {
        this.x = x;
        this.y = y;
    }

    public void setParentList(ArrayList<BaseEnemy> parentList) {
        this.parentList = parentList;
    }

    public void removeFromList() {
        if (parentList != null) {
            parentList.remove(this);
        }
    }

    public abstract void draw(Graphics g);

    public abstract void update();

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


}
