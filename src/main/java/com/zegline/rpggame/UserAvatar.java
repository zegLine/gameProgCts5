package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;
import main.java.com.zegline.rpggame.GameEntity.GameEntity;
import main.java.com.zegline.rpggame.GameEntity.ShopOwner;
import main.java.com.zegline.rpggame.Items.BaseArmor;
import main.java.com.zegline.rpggame.Items.Item;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class UserAvatar {

    private Image[] sprites;
    public int sprite_used;

    private int speed;
    private Color c;

    private int x;
    private int y;

    private BulletType currentBullet;

    public static int mula;

    public ShopOwner currentVendor = null;

    public boolean canStartWave = false;
    private double runFactor;

    enum AnimationState{
        UP,DOWN,LEFT,RIGHT
    }
    AnimationState as = AnimationState.RIGHT;;
    AnimationState prevAs = AnimationState.UP;
    int animationCounter = 0;

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

    public static Item item_in_hand = null;

    public static BaseArmor armor_equipped = null;

    public int health;

    public int stamina;

    public int armor;

    private int radius;

    public UserAvatar(Color c, int x, int y, int speed, int radius, String[] textures, int nSprites) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        currentBullet = BulletType.BASIC;

        runFactor = 1.0;

        health = 100;
        armor = 20;
        stamina = 100;
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

        if (canStartWave) {
            g.setFont(MainGamePanel.retroFont);
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Press X to start wave", x + 60, y + 70);
        }
        //g.setColor(c);
        //g.fillRect(20, 20, health, 20);
    }

    public void equipArmor(BaseArmor armor) {
        armor_equipped = armor;
        items_equipped.remove(armor);
        this.armor = armor_equipped.armorHealth;
    }

    // Separate methods for moving the avatar in each direction
    public void moveUp() {
        this.y -= speed * runFactor;  // Move upwards by decrementing the y-coordinate
    }

    public void moveDown() {
        this.y += speed* runFactor;  // Move downwards by incrementing the y-coordinate
    }

    public void moveLeft() {
        this.x -= speed* runFactor;  // Move leftwards by decrementing the x-coordinate
    }

    public void moveRight() {
        this.x += speed* runFactor;  // Move rightwards by incrementing the x-coordinate
    }

    public void handleMovement(boolean[] arrowKeyPressed) {

        boolean moving = false;



        if (arrowKeyPressed[0]) {
            moveLeft();moving =true;
        }

        if (arrowKeyPressed[1]) {
            moveRight();moving =true;
        }
        if (arrowKeyPressed[2]) {
            moveUp();moving =true;
        }
        if (arrowKeyPressed[3]) {
            moveDown();moving =true;
        }

        if (ChatGame.shiftPressed && stamina > 0 && moving) {
            runFactor = 1.5;
            if (new Random().nextInt(10) > 8) {
                stamina -= 1;
            }

        } else {
            runFactor = 1.0;
        }

    }

    public void update() {
        this.handleMovement(ChatGame.arrowKeyPressed);
        this.handleCollision();
        this.shoot();
        this.handleAnimation(ChatGame.arrowKeyPressed);
        this.handleGoShop();
        if (MainGamePanel.currentGameState == MainGamePanel.GameState.SHOP_SCREEN) {
            handleShopCollision();
        }
        if (MainGamePanel.currentGameState == MainGamePanel.GameState.GAMEPLAY) {
            handleWaveStartCollision();
        }
    }

    private void handleWaveStartCollision() {

        if (x > 160 && x < 290 && y > 315 && y < 440 && ChatGame.waveOngoing == false) {
            canStartWave = true;
        } else {
            canStartWave = false;
        }

    }

    private void handleAnimation(boolean[] arrowKeyPressed) {





        double speedOfAnimation = 6;
        if(arrowKeyPressed[2] ^ arrowKeyPressed[3]) {
            if(arrowKeyPressed[2]) {
                as = AnimationState.UP;
                if(as != prevAs) {

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
        if (MainGamePanel.currentGameState == MainGamePanel.GameState.GAMEPLAY && ChatGame.waveOngoing == false) {
            if (this.x > ChatGame.screenWidth - 130 && this.y > ChatGame.screenHeight / 2 - 48 && this.y < ChatGame.screenHeight / 2 + 48) {
                MainGamePanel.currentGameState = MainGamePanel.GameState.SHOP_SCREEN;
                this.x = 100;
            }
        }

        if (MainGamePanel.currentGameState == MainGamePanel.GameState.SHOP_SCREEN) {
            if (this.x < 100 && this.y > ChatGame.screenHeight / 2 - 48 && this.y < ChatGame.screenHeight / 2 + 48) {
                MainGamePanel.currentGameState = MainGamePanel.GameState.GAMEPLAY;
                this.x = 1100;
            }
        }
    }

    private void shoot() {
        if(ChatGame.mouseClicked) {

            ChatGame.mouseClicked = false;
            System.out.println("player mouse clicked");
            //new BulletFactory().shoot(currentBullet);
            if (item_in_hand != null) {
                item_in_hand.act();
            }
        }
    }

    public void doDamage(int dmg){


            double multiplier = 1.0;
            if (armor_equipped != null) {
                // use equipped armor to reduce damage to main armor

                multiplier = armor_equipped.armorMultiplier;

                armor_equipped.armorHealth -= (int)((dmg/2) * multiplier);

                // destroy armor
                if (armor_equipped.armorHealth < 0) {
                    armor_equipped = null;
                }
                System.out.println("armor damge: " + (int)((dmg/2) * multiplier));
                armor -= (int) ((dmg/2) * multiplier);
                return;
            }



        armor = 0;
        health -= dmg;
        if(health < 0 ){
            death();
        }
    }

    private void death() {
        System.out.println("death");
        MainGamePanel.currentGameState = MainGamePanel.GameState.DEATH_SCREEN;
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

    public void handleShopCollision() {
        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();

            if(!(entity instanceof ShopOwner)){
                continue;
            }
            ShopOwner vendor = (ShopOwner)entity;
            double dx = this.x - vendor.getX() - (double) vendor.getRadius() /2;
            double dy = this.y - vendor.getY() - (double) vendor.getRadius() /2;
            int distance = (int) Math.sqrt(dx * dx + dy * dy);



            // Check if the circles overlap (collision occurs when distance <= sum of radii)
            if (distance <= (this.radius + vendor.getRadius())) {
                System.out.println("in shop");
                System.out.println(vendor.message);
                this.currentVendor = vendor;
                break;

            } else {
                System.out.println(555);
                this.currentVendor = null;
            }
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


