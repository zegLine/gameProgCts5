package main.java.com.zegline.rpggame.GameEntity.Enemies;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;
import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.GameEntity.Bullets.EnemyBulletFactory;

import java.awt.*;

public class ShootingEnemy extends BaseEnemy {
    private long lastShotTime;

    private static long shotCooldown;

    private static final int DAMAGECONST = 2;



    public ShootingEnemy(int x, int y, int level) {
        super(x,y,level, new String[]{"skewerstalker/SkeweringStalker.png", },1);
        this.x = x;
        this.y = y;
        this.speed = 2 + level/2;
        this.c = Color.RED;

        radius = 32;

        this.damage = DAMAGECONST * level;

        this.health = 10 + 2 * level;

        shotCooldown = 200 - (10L * level);

        lastCollisionTime = 0;

        this.maxHealth = this.health;

        this.state = 0;

    }

    public void update() {
        this.handleMovement();
        this.handleCollision();
        this.shoot();
    }

    private void shoot() {
        if(lastShotTime < shotCooldown) {
            lastShotTime++;
            return;
        }
        lastShotTime = 0;
        new EnemyBulletFactory(BulletType.SHOOTING_ENEMY,this.x,this.y);
    }

    public void handleMovement() {
        //angleOfPlayer = angleToPlayer();
        double dx = this.x - ChatGame.max.getX();
        double dy = this.y - ChatGame.max.getY();
        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        int distanceFromPlayer = 300;
        if(distance > distanceFromPlayer+20) {


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
        } else if(distance < distanceFromPlayer - 20) {
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

            if (x + (2 * radius) > ChatGame.screenWidth) {
                moveLeft();
            }
            if(x - radius < 0){
                moveRight();
            }
            if(y + (2 * radius) > ChatGame.screenHeight) {
                moveUp();
            }
            if(y - radius < 0) {
                moveDown();
            }

    }

    public void doDamage(int damage) {
        this.health -= damage;
        if(health < 0) {
            death();
        }
    }



}
