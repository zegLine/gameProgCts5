package main.java.com.zegline.rpggame.GameEntity.Enemies;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.UserAvatar;

import java.awt.*;

public class BasicEnemy extends BaseEnemy {
    private long lastCollisionTime;


    private static final int DAMAGECONST = 2;

    @Override
    public void addRewardsToPlayer(){
        UserAvatar.setMula(UserAvatar.getMula() + 20);
    }

    public BasicEnemy(int x, int y, int level) {
        super(x,y,level, new String[]{"flyingeyeenemy/FloatingEyeEnemy.png", "flyingeyeenemy/FloatingEyeEnemyFlipped.png"},2);
        this.x = x;
        this.y = y;
        this.speed = 1 + level/5;
        this.c = Color.RED;

        radius = 32;

        this.damage = DAMAGECONST * level;

        this.health = 10 + 2 * level;

        lastCollisionTime = 0;

        this.maxHealth = this.health;

        this.state = 0;

        this.COOLDOWN_DURATION = 2000;
    }



    public void handleMovement() {
        //angleOfPlayer = angleToPlayer();
        if(x > ChatGame.max.getX()) {
            moveLeft();
            state = 1;

        }

        if(x < ChatGame.max.getX()) {
            moveRight();
            state = 0;
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




}
