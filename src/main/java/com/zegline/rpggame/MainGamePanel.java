package main.java.com.zegline.rpggame;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

class MainGamePanel extends JPanel {

    Image bg;
    Image startBtn;
    Image quitBtn;
    Image creditsBtn;

    ClassLoader classLoader = World.class.getClassLoader();

    enum GameState {
        MAIN_MENU,
        GAMEPLAY,
        PAUSE_MENU,
        SHOP_SCREEN
    }

    public static GameState currentGameState;

    private boolean showCredits = false;

    private static final int TARGET_FPS = 120; // Target frames per second
    private static final long TARGET_TIME = 1000000000 / TARGET_FPS; // Target time per frame in nanoseconds

    public MainGamePanel() {
        setBackground(Color.BLACK);
        currentGameState = GameState.MAIN_MENU;

        bg = new ImageIcon(classLoader.getResource("bg.jpg")).getImage();
        startBtn = new ImageIcon(classLoader.getResource("btn.png")).getImage();
        quitBtn = new ImageIcon(classLoader.getResource("btn.png")).getImage();
        creditsBtn = new ImageIcon(classLoader.getResource("btn.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (currentGameState) {
            case MAIN_MENU:
                drawMainMenu(g);
                break;
            case GAMEPLAY:
                drawGameplay(g);
                break;
            case PAUSE_MENU:
                drawPauseMenu(g);
                break;
            case SHOP_SCREEN:
                drawShopScreen(g);
                break;
        }
    }

    private void drawShopScreen(Graphics g) {
        World.drawItemsEquipped(g);

        ChatGame.max.draw(g);

        drawMula(g);

        drawXYCoords(g);

        drawCommandsText(g);
    }

    private void drawMula(Graphics g) {
        // Draw Money
        g.setColor(Color.lightGray);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("$" + String.valueOf(UserAvatar.getMula()), getWidth() - 50, 30);
    }

    private void drawXYCoords(Graphics g) {
        // Draw X and Y coord of camera
        g.drawString("x:" + String.valueOf(ChatGame.max.getX()), getWidth() - 50, 50);
        g.drawString("y:" + String.valueOf(ChatGame.max.getY()), getWidth() - 50, 65);
    }

    private void drawCommandsText(Graphics g) {
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

    private void drawMainMenu(Graphics g) {
        setBackground(Color.BLACK);

        g.drawImage(bg, 0, 0, 1920, 1080, null);

        g.drawImage(startBtn, 200, 200, 300, 100, null);
        g.drawImage(quitBtn, 200, 400, 300, 100, null);
        g.drawImage(creditsBtn, 200, 600, 300, 100, null);

        if (showCredits) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Osama al Kamel", 1400, 300);
            g.drawString("Joshua Oldridge", 1400, 350);
            g.drawString("Eugen-George Zdrincu", 1400, 400);
            g.drawString("Firas Bazerbashi", 1400, 450);
            g.drawString("Amjed", 1400, 500);
        }
    }

    private void drawGameplay(Graphics g) {
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


        drawMula(g);

        drawXYCoords(g);

        drawCommandsText(g);
    }

    private void drawPauseMenu(Graphics g) {
        // Draw pause menu graphics here
        // For example:
        g.setColor(Color.WHITE);
        g.drawString("Pause Menu", getWidth() / 2, getHeight() / 2);
    }

    public void startGameLoop() {
        Thread gameLoop = new Thread(() -> {
            long lastLoopTime = System.nanoTime();

            while (true) {
                long now = System.nanoTime();
                long updateLength = now - lastLoopTime;
                lastLoopTime = now;
                double delta = updateLength / ((double) TARGET_TIME);

                // Update current game state
                updateCurrentState(delta);

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

    private void updateCurrentState(double delta) {
        switch (currentGameState) {
            case MAIN_MENU:
                updateMainMenu(delta);
                break;
            case GAMEPLAY:
                updateGameplay(delta);
                break;
            case PAUSE_MENU:
                updatePauseMenu(delta);
                break;
            case SHOP_SCREEN:
                updateShopScreen(delta);
                break;
        }
    }

    private void updateShopScreen(double delta) {

        if(ChatGame.commandMode) {
            return;
        }

        ChatGame.max.update();
    }

    private void updateMainMenu(double delta) {
        // Update main menu logic here

        if (ChatGame.mouseClicked) {
            ChatGame.mouseClicked = false;
            if (ChatGame.mouseX > 200 && ChatGame.mouseX < 500 && ChatGame.mouseY > 200 && ChatGame.mouseY < 300) {
                // START BUTTON CLICKED START GAME
                currentGameState = GameState.GAMEPLAY;
            }

            if (ChatGame.mouseX > 200 && ChatGame.mouseX < 500 && ChatGame.mouseY > 400 && ChatGame.mouseY < 500) {
                // CREDITS BUTTON CLICKED SHOW CREDITS
                showCredits = !showCredits;
            }

            if (ChatGame.mouseX > 200 && ChatGame.mouseX < 500 && ChatGame.mouseY > 600 && ChatGame.mouseY < 700) {
                // EXIT BUTTON CLICKED END GAME
                System.exit(0);
            }
        }

    }

    private void updateGameplay(double delta) {
        if(ChatGame.commandMode) {
            return;
        }

        ChatGame.max.update();

        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity enemy = iterator.next();
            enemy.update();
        }

        shootPlayerLoop();
        
        deathLoop();
    }

    private void shootPlayerLoop() {
        while(!ChatGame.bulletCreateList.isEmpty()){
            ChatGame.bulletCreateList.pop().shootPlayer();
        }
    }

    private void updatePauseMenu(double delta) {
        // Update pause menu logic here
    }

    private void deathLoop() {
        while(!ChatGame.deathList.isEmpty()) {
            ChatGame.deathList.pop().removeFromList();
        }

    }
}