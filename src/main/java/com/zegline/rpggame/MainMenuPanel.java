package main.java.com.zegline.rpggame;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    public MainMenuPanel() {
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawString("FUCK THIS", 10, 10);

    }

}
