package main.java.com.zegline.rpggame;

import java.awt.*;

public class UserAvatar {

    private int speed;
    private Color c;

    private int x;
    private int y;



    private int radius;

    public UserAvatar(Color c, int x, int y, int speed, int radius) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
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
    }

    private void shoot() {
        if(ChatGame.mouseClicked == true) {
            ChatGame.mouseClicked = false;



            double dx = ChatGame.mouseX - ChatGame.max.getX();
            double dy = ChatGame.mouseY - ChatGame.max.getY();
            double angle = Math.atan2(dy, dx); // Angle in radians

            new Bullet(x,y,angle);
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


