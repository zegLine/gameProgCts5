package main.java.com.zegline.rpggame;

import java.awt.*;

public class BasicEnemy extends BaseEnemy {
    private long lastCollisionTime;

    private static final long COOLDOWN_DURATION = 2000;

    private static final int DAMAGECONST = 2;



    public BasicEnemy(int x, int y, int level) {
        super(x,y,level);
        this.x = x;
        this.y = y;
        this.speed = 2 + level/3;
        this.c = Color.RED;

        radius = 28;

        this.damage = DAMAGECONST * level;

        this.health = 10 + 2 * level;

        lastCollisionTime = 0;

        this.maxHealth = this.health;

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

    public void doDamage(int damage) {
        this.health -= damage;
        if(health < 0) {
            death();
        }
    }



}
