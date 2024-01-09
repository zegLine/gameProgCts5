package main.java.com.zegline.rpggame;

import java.awt.*;

public class BasicEnemy extends BaseEnemy {
    private int speed;



    private Color c;

    private int x;
    private int y;
    private int damage;
    private int radius;
    private long lastCollisionTime;

    private static final long COOLDOWN_DURATION = 2000;

    public BasicEnemy(int x, int y, int level) {
        super(x,y,level);
        this.x = x;
        this.y = y;
        speed = 2 + level;
        c = Color.RED;

        radius = 28;

        int DAMAGECONST = 2;
        damage = DAMAGECONST * level;

        lastCollisionTime = 0;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect(x - radius, y - radius, radius*2, radius*2);
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

    public void handleMovement(boolean[] arrowKeyPressed) {
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
        this.handleMovement(ChatGame.arrowKeyPressed);

        this.handleCollision();
    }

    private void handleCollision() {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastCollisionTime >= COOLDOWN_DURATION) {

            int dx = this.x - ChatGame.max.getX();
            int dy = this.y - ChatGame.max.getY();
            int distance = (int) Math.sqrt(dx * dx + dy * dy);

            // Check if the circles overlap (collision occurs when distance <= sum of radii)
            if (distance <= (this.radius + ChatGame.max.getRadius())) {
                System.out.println("hit");
                lastCollisionTime = currentTime;
            }
        }
    }


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
