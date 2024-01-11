package main.java.com.zegline.rpggame;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;


public class UserAvatar {

    private int speed;
    private Color c;

    private int x;
    private int y;

    private BulletType currentBullet;

    private static int mula;

    public static boolean enoughMoneyAndBuy(int amount) {
        if (amount > mula) return false;

        mula -= amount;

        return true;
    }

    public static int getMula(){
        return mula;
    }

    public static void setMula(int amount) {
        mula = amount;
    }

    public static Set<Item> items_equipped = new HashSet<>();

    public static Set<Item> items_available = new HashSet<>();


    private int radius;

    public UserAvatar(Color c, int x, int y, int speed, int radius) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        currentBullet = BulletType.BASIC;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect(x - radius, y - radius, radius * 2, radius * 2);
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

        if (arrowKeyPressed[0]) {
            moveLeft();
        }

        if (arrowKeyPressed[1]) {
            moveRight();
        }
        if (arrowKeyPressed[2]) {
            moveUp();
        }
        if (arrowKeyPressed[3]) {
            moveDown();
        }

    }

    public void update() {
        this.handleMovement(ChatGame.arrowKeyPressed);
        this.handleCollision();
        this.shoot();
        this.handleGoShop();
    }

    private void handleGoShop() {
        if (MainGamePanel.currentGameState == MainGamePanel.GameState.GAMEPLAY) {
            if (this.x > 1800 && this.y > 500 && this.y < 700) {
                MainGamePanel.currentGameState = MainGamePanel.GameState.SHOP_SCREEN;
                this.x = 100;
            }
        }

        if (MainGamePanel.currentGameState == MainGamePanel.GameState.SHOP_SCREEN) {
            if (this.x < 100 && this.y > 500 && this.y < 700) {
                MainGamePanel.currentGameState = MainGamePanel.GameState.GAMEPLAY;
                this.x = 1800;
            }
        }
    }

    private void shoot() {
        if(ChatGame.mouseClicked == true) {
            ChatGame.mouseClicked = false;
            System.out.println("shoot");

            new BulletFactory().shoot(currentBullet);

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


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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
}


