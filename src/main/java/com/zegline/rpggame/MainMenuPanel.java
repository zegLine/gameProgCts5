package main.java.com.zegline.rpggame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainMenuPanel extends JPanel {

    Image bg;
    Image startBtn;
    Image quitBtn;
    Image creditsBtn;

    public MainMenuPanel() {
        setBackground(Color.BLACK);
        ClassLoader classLoader = World.class.getClassLoader();

        bg = new ImageIcon(classLoader.getResource("bg.jpg")).getImage();
        startBtn = new ImageIcon(classLoader.getResource("btn.png")).getImage();
        quitBtn = new ImageIcon(classLoader.getResource("btn.png")).getImage();
        creditsBtn = new ImageIcon(classLoader.getResource("btn.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, 0, 1920, 1080, null);

        g.drawImage(startBtn, 200, 200, 300, 100, null);
        g.drawImage(quitBtn, 200, 400, 300, 100, null);
        g.drawImage(creditsBtn, 200, 600, 300, 100, null);
    }

}
