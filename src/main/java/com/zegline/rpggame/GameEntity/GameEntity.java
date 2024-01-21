package main.java.com.zegline.rpggame.GameEntity;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.GameEntity.Enemies.BaseEnemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameEntity {
    protected double speed;

    protected Color c;

    protected double x;
    protected double y;
    protected int damage;
    protected int radius;

    private ArrayList<GameEntity> parentList;

    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
        setParentList(ChatGame.gameEntityList);
    }

    public GameEntity(int x, int y, boolean addToList) {
        this.x = x;
        this.y = y;
        if (addToList) {
            setParentList(ChatGame.gameEntityList);
        }

    }

    public void setParentList(ArrayList<GameEntity> parentList) {
        this.parentList = parentList;
        parentList.add(this);
    }

    public void removeFromList() {
        if (parentList != null) {
            parentList.remove(this);
        }
    }

    protected double angleToPlayer(){
        double dx = this.x - ChatGame.max.getX();
        double dy = this.y - ChatGame.max.getY();
        return Math.atan2(dy, dx);
    }

    public abstract void draw(Graphics g);

    public abstract void update();

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
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


    protected void death() {
        ChatGame.deathList.push(this);
    }

}
