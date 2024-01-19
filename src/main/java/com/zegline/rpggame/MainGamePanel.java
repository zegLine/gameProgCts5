package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Enemies.BaseEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.EnemyFactory;
import main.java.com.zegline.rpggame.GameEntity.GameEntity;
import main.java.com.zegline.rpggame.GameEntity.ShopOwner;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class MainGamePanel extends JPanel {



    Image bg;
    Image startBtn;
    Image quitBtn;
    Image creditsBtn;

    Image hudImg;

    Image cashImage;

    public static Font retroFont;

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

        hudImg = new ImageIcon(classLoader.getResource("StatusHud.png")).getImage();

        cashImage = new ImageIcon(classLoader.getResource("hudCoin.png")).getImage();

        try {
            retroFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/ARCADECLASSIC.TTF")).deriveFont(Font.PLAIN, 28);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        World.drawShop(g);

        //Draw loop for GameEntities
        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();
            if (entity instanceof ShopOwner) {
                entity.draw(g);
            }

        }

        World.drawItemsEquipped(g);

        ChatGame.max.draw(g);

        drawXYCoords(g);

        drawCommandsText(g);

        try {
            drawHud(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        drawDebugInfo(g);
    }


    private void drawXYCoords(Graphics g) {
        // Draw X and Y coord of camera
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("x:" + String.valueOf(ChatGame.max.getX()), getWidth() - 50, 65);
        g.drawString("y:" + String.valueOf(ChatGame.max.getY()), getWidth() - 50, 80);
    }

    private void drawCommandsText(Graphics g) {
        if (ChatGame.commandMode) {
            g.setColor(Color.BLACK);
            g.fillRect(0, ChatGame.screenHeight - 80, ChatGame.screenWidth, 80);

            // Draw the typed text in white at the bottom of the screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font size and style
            String textToRender = ChatGame.inputText.toString();
            g.drawString(textToRender, 10, getHeight() - 10); // Position it at the bottom
        }


        // Check if the boolean is set to true and draw the question mark
        if (!CommandHandler.commandError.isEmpty()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36)); // Adjust the font size and style
            g.drawString("?" + CommandHandler.commandError, 500, 500); // Adjust the position of the question mark
        }


    }

    private void drawDebugInfo(Graphics g) {

        if (!ChatGame.debugMode) return;

        // Save color and font
        Color prevColor = g.getColor();
        Font prevFont = g.getFont();

        // Temp set new debug color and font
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));

        g.drawString("DBG MODE",getWidth() - 150, 240);
        g.drawString("MS XY " + ChatGame.mouseX + " " + ChatGame.mouseY,getWidth() - 150, 250);
        g.drawString("GS " + currentGameState,getWidth() - 150, 270);
        String waveActive;
        if (ChatGame.waveOngoing) waveActive = "ACTV"; else waveActive = "INAC";
        g.drawString("WV " + ChatGame.currentWave + "(" + waveActive + ")",getWidth() - 150, 290);
        g.drawString("CAN STR WAVE " + ChatGame.max.canStartWave,getWidth() - 150, 310);
        if (ChatGame.enemyFactory != null) {
            g.drawString("ENM RMN " + ChatGame.enemyFactory.enemiesRemaining,getWidth() - 150, 330);
            g.drawString("ENM ALV " + BaseEnemy.aliveEnemies ,getWidth() - 150, 350);
        }

        // Undo color and font
        g.setColor(prevColor);
        g.setFont(prevFont);
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
        g.setColor(Color.BLACK);
        //g.fillRect(0,0,300,64);

        ChatGame.max.draw(g);

        //Draw loop for GameEntities
        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();
            if (entity instanceof ShopOwner) continue;
            entity.draw(g);
        }




        try {
            drawHud(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        drawDebugInfo(g);
        drawWaveInfo(g);

        drawXYCoords(g);

        drawCommandsText(g);
    }

    private void drawWaveInfo(Graphics g) {
        if (ChatGame.waveOngoing) {
            g.setFont(retroFont.deriveFont(48.0f));
            g.setColor(Color.ORANGE);
            g.drawString("WAVE " + ChatGame.currentWave, 600, 50);
        }

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

        if(ChatGame.enemyFactory != null) {
            ChatGame.enemyFactory.spawnEnemies();
        }
        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity enemy = iterator.next();
            enemy.update();
        }

        shootPlayerLoop();
        
        deathLoop();

        checkWaveFinishedLoop();


    }

    private void checkWaveFinishedLoop() {
        if (ChatGame.enemyFactory != null) {
            System.out.println(BaseEnemy.aliveEnemies);
            if (ChatGame.enemyFactory.enemiesRemaining == 0 && ChatGame.waveOngoing) {

                Waves.finishWave();
            }
        }


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

    private void drawHud(Graphics g) throws IOException {




        g.setColor(Color.GREEN);
        g.setFont(retroFont);


        g.drawImage(cashImage, getWidth() - 91, 21, null);
        g.drawString(String.valueOf(UserAvatar.getMula()), getWidth() - 70, 40);

        g.drawImage(hudImg, 10, 10, null);

        g.setColor(Color.RED);
        g.fillRect(85, 17, ChatGame.max.health, 10);

        g.setColor(Color.GREEN);
        g.fillRect(85, 39, ChatGame.max.stamina, 10);

        g.setColor(Color.CYAN);
        g.fillRect(85, 58, ChatGame.max.armor, 10);

        drawItemInHand(g);
    }

    private void drawItemInHand(Graphics g) {
        if (UserAvatar.item_in_hand != null) {
            UserAvatar.item_in_hand.draw(g);
        }

    }

}