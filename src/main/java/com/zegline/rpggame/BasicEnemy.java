package main.java.com.zegline.rpggame;

import java.awt.*;

public class BasicEnemy extends GameEntity {
    private double speed;


    private Color c;

    private double x;
    private double y;
    private int damage;
    private int radius;
    private long lastCollisionTime;

    private static final long COOLDOWN_DURATION = 2000;

    private static final int DAMAGECONST = 2;


    public BasicEnemy(int x, int y, int level) {
        super(x,y);
        this.x = x;
        this.y = y;
        speed = 2 + level;
        c = Color.RED;

        radius = 28;

        damage = DAMAGECONST * level;

        lastCollisionTime = 0;

        setParentList(ChatGame.enemyList);
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);
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
        if(x > ChatGame.max.getX()) {
            moveLeft();
        }

        if(x < ChatGame.max.getX()) {
            moveRight();
        }

        if(y > ChatGame.max.getY()) {
            moveUp();
        }

        if(y < ChatGame.max.getY()) {
            moveDown();
        }

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
                System.out.println("hit");
                lastCollisionTime = currentTime;
            }
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
