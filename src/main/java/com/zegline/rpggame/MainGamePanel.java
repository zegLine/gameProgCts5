package main.java.com.zegline.rpggame;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

class MainGamePanel extends JPanel {

    private static final int TARGET_FPS = 120; // Target frames per second
    private static final long TARGET_TIME = 1000000000 / TARGET_FPS; // Target time per frame in nanoseconds

    public MainGamePanel() {
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {

        //World.simpleAvatar.draw(g);
        World.drawMap(g,0, 0, getWidth(), getHeight());
        World.drawItemsEquipped(g);

        ChatGame.max.draw(g);

        //Draw loop for GameEntities
        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();
            entity.draw(g);
        }


        // Draw Money
        g.setColor(Color.lightGray);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("$" + String.valueOf(World.getMula()), getWidth() - 50, 30);

        // Draw X and Y coord of camera
        g.drawString("x:" + String.valueOf(ChatGame.max.getX()), getWidth() - 50, 50);
        g.drawString("y:" + String.valueOf(ChatGame.max.getY()), getWidth() - 50, 65);

        // Draw the typed text in white at the bottom of the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font size and style
        String textToRender = ChatGame.inputText.toString();
        g.drawString(textToRender, 10, getHeight() - 10); // Position it at the bottom

        // Check if the boolean is set to true and draw the question mark
        if (!CommandHandler.commandError.isEmpty()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24)); // Adjust the font size and style
            g.drawString("?" + CommandHandler.commandError, 50, 50); // Adjust the position of the question mark
        }
    }

    public void startGameLoop() {
        Thread gameLoop = new Thread(() -> {
            long lastLoopTime = System.nanoTime();

            while (true) {
                long now = System.nanoTime();
                long updateLength = now - lastLoopTime;
                lastLoopTime = now;
                double delta = updateLength / ((double) TARGET_TIME);

                // Call the update and render methods here
                update(delta);
                repaint(); // This will call paintComponent

                try {
                    long sleepTime = (lastLoopTime - System.nanoTime() + TARGET_TIME) / 1000000; // Time to sleep in milliseconds
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameLoop.start();
    }

    private void update(double delta) {
        if(ChatGame.commandMode) {
            return;
        }

        ChatGame.max.update();

        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity enemy = iterator.next();
            enemy.update();
        }

        deathLoop();


    }

    private void deathLoop() {
        while(!ChatGame.deathList.isEmpty()) {
            ChatGame.deathList.pop().removeFromList();
        }

    }
}