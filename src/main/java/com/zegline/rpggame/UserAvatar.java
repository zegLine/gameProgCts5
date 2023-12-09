package main.java.com.zegline.rpggame;

import java.awt.*;

public class UserAvatar {

    private Color c;

    private int x;
    private int y;
    private int width;
    private int height;

    public UserAvatar(Color c, int x, int y, int width, int height) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, width, height);
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
}
