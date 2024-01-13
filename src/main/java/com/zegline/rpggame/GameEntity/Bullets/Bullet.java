package main.java.com.zegline.rpggame.GameEntity.Bullets;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.GameEntity.Enemies.BaseEnemy;
import main.java.com.zegline.rpggame.GameEntity.GameEntity;

import java.awt.*;
import java.util.Iterator;

public abstract class Bullet extends GameEntity {
    private double angle;
    private double speed;

    protected Color c;

    protected double x;
    protected double y;
    protected int damage;
    protected int radius;


    private static final int DAMAGECONST = 2;


    public Bullet(int x, int y, double angle) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.angle = angle;

        speed = 20;
        c = Color.BLACK;

        radius = 5;

        damage = DAMAGECONST;
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
        double dx = speed * Math.cos(angle);  // Change in x (horizontal movement)
        double dy = speed * Math.sin(angle);  // Change in y (vertical movement)


        x += dx;
        y += dy;

    }

    public void update() {
        this.handleMovement();
        this.handleCollision();
    }

    private void handleCollision() {
        if (x > ChatGame.screenWidth || x < 0 || y > ChatGame.screenHeight || y < 0) {
            //System.out.println("bullet dead");
            this.death();
        }

        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();

            if(!(entity instanceof BaseEnemy)){
                continue;
            }
            BaseEnemy enemy = (BaseEnemy)entity;
            double dx = this.x - enemy.getX();
            double dy = this.y - enemy.getY();
            int distance = (int) Math.sqrt(dx * dx + dy * dy);

            // Check if the circles overlap (collision occurs when distance <= sum of radii)
            if (distance <= (this.radius + enemy.getRadius())) {
                System.out.println("hit");
                enemy.doDamage(damage);
                death();
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
