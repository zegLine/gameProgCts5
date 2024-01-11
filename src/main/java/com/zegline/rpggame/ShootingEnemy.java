package main.java.com.zegline.rpggame;

import java.awt.*;

public class ShootingEnemy extends BaseEnemy {
    private long lastCollisionTime;

    private static final long COOLDOWN_DURATION = 2000;

    private static final int DAMAGECONST = 2;



    public ShootingEnemy(int x, int y, int level) {
        super(x,y,level, new String[]{"skewerstalker/SkeweringStalker.png", },1);
        this.x = x;
        this.y = y;
        this.speed = 10 + level/2;
        this.c = Color.RED;

        radius = 32;

        this.damage = DAMAGECONST * level;

        this.health = 10 + 2 * level;

        lastCollisionTime = 0;

        this.maxHealth = this.health;

        this.state = 0;

    }



    public void handleMovement() {
        angleOfPlayer = angleToPlayer();
        double dx = this.x - ChatGame.max.getX();
        double dy = this.y - ChatGame.max.getY();
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        if(distance > 520) {


            if (x > ChatGame.max.getX()) {
                moveLeft();


            }

            if (x < ChatGame.max.getX()) {
                moveRight();

            }

            if (y > ChatGame.max.getY()) {
                moveUp();
            }

            if (y < ChatGame.max.getY()) {
                moveDown();
            }
        } else if(distance < 480) {
            if (x > ChatGame.max.getX()) {
                moveRight();


            }

            if (x < ChatGame.max.getX()) {
                moveLeft();

            }

            if (y > ChatGame.max.getY()) {
                moveDown();
            }

            if (y < ChatGame.max.getY()) {
                moveUp();
            }
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
