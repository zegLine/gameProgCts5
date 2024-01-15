package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;


public class UserAvatar {

    private Image[] sprites;
    public int sprite_used;

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

    private int health;

    private int radius;

    public UserAvatar(Color c, int x, int y, int speed, int radius, String[] textures, int nSprites) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        currentBullet = BulletType.BASIC;

        health = 100;
        sprite_used = 0;
        sprites = new Image[nSprites];

        for (int i = 0; i < nSprites; i++) {
            sprites[i] =  new ImageIcon(World.class.getClassLoader().getResource("player/" + textures[i]+".png")).getImage();
        }
    }

    public void draw(Graphics g) {
        //g.setColor(c);
        //g.fillRect(x - radius, y - radius, radius * 2, radius * 2);


        g.drawImage(sprites[sprite_used], x - radius, y - radius, 64, 64, null);
        g.setColor(c);
        g.fillRect(20, 20, health, 20);
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
        this.handleAnimation(ChatGame.arrowKeyPressed);
        this.handleGoShop();

    }

    enum AnimationState{
        UP,DOWN,LEFT,RIGHT
    }
    AnimationState as = AnimationState.RIGHT;;
    AnimationState prevAs = AnimationState.UP;

    int animationCounter = 0;
    private void handleAnimation(boolean[] arrowKeyPressed) {





        double speedOfAnimation = 6;
        if(arrowKeyPressed[2] ^ arrowKeyPressed[3]) {
            if(arrowKeyPressed[2]) {
                as = AnimationState.UP;
                if(as != prevAs) {
                    System.out.println("UP change");

                    prevAs = as;
                    sprite_used = 4;
                    animationCounter = 0;
                } else {
                    if(animationCounter < 5 * speedOfAnimation) {
                        animationCounter++;
                    } else {
                        animationCounter = 0;
                        sprite_used = sprite_used >= 7 ? 4 : sprite_used + 1;
                    }
                }
            } else if (arrowKeyPressed[3]) {
                as = AnimationState.DOWN;
                if(as != prevAs) {
                    System.out.println("DOWN change");

                    prevAs = as;
                    sprite_used = 0;
                    animationCounter = 0;
                } else {
                    if(animationCounter < 5 * speedOfAnimation) {
                        animationCounter++;
                    } else {
                        animationCounter = 0;
                        sprite_used = sprite_used >= 3 ? 0 : sprite_used + 1;

                    }
                }
            }

        } else if (arrowKeyPressed[0] ^ arrowKeyPressed[1]){
            if(arrowKeyPressed[0]) {
                as = AnimationState.LEFT;
                if(as != prevAs) {
                    System.out.println("LEFT change");

                    prevAs = as;
                    sprite_used = 12;
                    animationCounter = 0;
                } else {
                    if(animationCounter < 5 * speedOfAnimation) {
                        animationCounter++;
                    } else {
                        animationCounter = 0;
                        sprite_used = sprite_used >= 15 ? 12 : sprite_used + 1;
                    }
                }
            } else if (arrowKeyPressed[1]) {
                as = AnimationState.RIGHT;
                if(as != prevAs) {
                    System.out.println("RIGHT change");

                    prevAs = as;
                    sprite_used = 8;
                    animationCounter = 0;
                } else {
                    if(animationCounter < 5 * speedOfAnimation) {
                        animationCounter++;
                    } else {
                        animationCounter = 0;
                        sprite_used = sprite_used >= 11 ? 8 : sprite_used + 1;
                    }
                }
            }
        } else {
            animationCounter = 0;
            switch (as) {
                case UP -> {
                    sprite_used = 5;
                    break;
                }
                case DOWN -> {
                    sprite_used = 1;
                    break;
                }
                case LEFT -> {
                    sprite_used = 13;
                    break;
                } case RIGHT -> {
                    sprite_used = 9;
                    break;
                }
            }
        }
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

    public void doDamage(int dmg){
        health -= dmg;
        if(health < 0 ){
            death();
        }
    }

    private void death() {
        System.out.println("death");
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


