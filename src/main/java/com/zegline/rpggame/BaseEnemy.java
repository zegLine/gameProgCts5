package main.java.com.zegline.rpggame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class BaseEnemy extends GameEntity{
    protected double speed;


    protected Color c;

    protected double x;
    protected double y;
    protected int damage;
    protected int radius;
    protected long lastCollisionTime;

    protected long COOLDOWN_DURATION;


    protected int health;

    protected int maxHealth;

    Image sprite[];

    protected int state;

    protected double angleOfPlayer;

    public BaseEnemy(int x, int y, int level, String[] texture,int numOfSprite) {
        super(x,y);
        angleOfPlayer = 0;
        this.x = x;
        this.y = y;
        lastCollisionTime = 0;
        sprite = new Image[numOfSprite];
        for(int i = 0; i < numOfSprite; i++) {
            sprite[i] = new ImageIcon(World.class.getClassLoader().getResource("enemies/" + texture[i])).getImage();
        }
    }

    public void draw(Graphics gbad) {
        Graphics2D g = (Graphics2D) gbad.create();
        g.setColor(c);
        g.setColor(Color.BLUE);
        g.fillRect((int) (x - radius), (int) (y - radius - 2), (int)((radius * 2) - ((1.0-((double)health/(double)maxHealth)) * (radius*2))),  8);

        g.rotate(angleOfPlayer,(int)(this.x),(int)(this.y));
        g.drawImage(sprite[state], (int) (x - radius), (int) (y - radius),radius * 2, radius * 2,null);
        //g.fillRect((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);


    }

    // Separate methods for moving the avatar in each direction
    public void moveUp() {
        this.y -= speed;  // Move upwards by decrementing the y-coordinate
    }

    public void moveDown() {
        this.y += speed;  // Move downwards by incrementing the y-coordinate
    }

    public void moveLeft() {
        this.x -= speed;  // Move leftwards by decrementing the x-coordinate
    }

    public void moveRight() {
        this.x += speed;  // Move rightwards by incrementing the x-coordinate
    }

    public void handleMovement() {


    }

    public void update() {
        this.handleMovement();
        this.handleCollision();
    }

    private void handleCollision() {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastCollisionTime >= COOLDOWN_DURATION) {

            double dx = this.x - ChatGame.max.getX();
            double dy = this.y - ChatGame.max.getY();
            int distance = (int) Math.sqrt(dx * dx + dy * dy);

            // Check if the circles overlap (collision occurs when distance <= sum of radii)
            if (distance <= (this.radius + ChatGame.max.getRadius())) {
                ChatGame.max.doDamage(damage);
                lastCollisionTime = currentTime;
            }
        }
    }

    public void doDamage(int damage) {
        this.health -= damage;
        if(health < 0) {
            death();
        }
    }


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


}
