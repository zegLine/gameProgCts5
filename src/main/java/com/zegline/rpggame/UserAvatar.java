package main.java.com.zegline.rpggame;

import java.awt.*;

public class UserAvatar {

    private int speed;
    private Color c;

    private int x;
    private int y;
    private int width;
    private int height;

    public UserAvatar(Color c, int x, int y, int speed, int width, int height) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, width, height);
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
}
